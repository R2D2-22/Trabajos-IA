import cv2
import numpy as np
import tensorflow as tf
from tensorflow.keras.applications.mobilenet_v2 import preprocess_input

# --- CONFIGURACIÓN ---
modelo_path = 'modelos_pro/mobilenet_finetuned.h5' # El nuevo modelo
clases_path = 'modelos_pro/clases.txt'
# ---------------------

print("Cargando modelo Fine-Tuned...")
try:
    model = tf.keras.models.load_model(modelo_path)
except:
    print("❌ Error: No encuentro el modelo finetuned.")
    exit()

with open(clases_path, 'r') as f:
    class_names = eval(f.read())
print(f"Clases: {class_names}")

cap = cv2.VideoCapture(1, cv2.CAP_DSHOW)
if not cap.isOpened(): cap = cv2.VideoCapture(0, cv2.CAP_DSHOW)

cap.set(cv2.CAP_PROP_FRAME_WIDTH, 1280)
cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 720)
cap.set(cv2.CAP_PROP_FOURCC, cv2.VideoWriter_fourcc(*'MJPG'))

font = cv2.FONT_HERSHEY_SIMPLEX

while True:
    ret, frame = cap.read()
    if not ret: break
    
    frame = cv2.flip(frame, 1)
    display_frame = frame.copy()
    
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    # Detectar rostros (A veces ayuda reducir minNeighbors si no detecta)
    faces = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml').detectMultiScale(gray, 1.3, 4)

    for (x, y, w, h) in faces:
        roi = frame[y:y+h, x:x+w]
        
        try:
            # 1. BGR a RGB
            roi_rgb = cv2.cvtColor(roi, cv2.COLOR_BGR2RGB)
            
            # 2. Resize a 160x160
            roi_resized = cv2.resize(roi_rgb, (160, 160))
            
            # 3. Pre-procesamiento
            img_array = np.array(roi_resized, dtype=np.float32)
            img_array = np.expand_dims(img_array, axis=0)
            
            # ESTA FUNCIÓN PONE LOS VALORES ENTRE -1 y 1
            img_preprocessed = preprocess_input(img_array)
            
            # 4. Predicción
            prediction = model.predict(img_preprocessed, verbose=0)
            idx = np.argmax(prediction)
            confidence = prediction[0][idx]
            
            nombre = class_names[idx].replace("pins_", "").replace("_", " ")

            if confidence > 0.8:
                color = (0, 255, 0) # Verde
                label = f"{nombre}: {confidence*100:.0f}%"
            else:
                color = (0, 0, 255) # Rojo
                # Mostrar en consola quién cree que es para depurar
                # print(f"Duda: {nombre} ({confidence:.2f})")
                label = "Desconocido"
            
            cv2.rectangle(display_frame, (x, y), (x+w, y+h), color, 2)
            cv2.putText(display_frame, label, (x, y-10), font, 0.8, color, 2)
            
        except Exception as e:
            pass

    cv2.imshow('Reconocimiento FINAL', display_frame)
    if cv2.waitKey(1) & 0xFF == ord('q'): break

cap.release()
cv2.destroyAllWindows()