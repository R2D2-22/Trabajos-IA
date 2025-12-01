import cv2
import os
import time

# --- CONFIGURACIÓN ---
nombre_carpeta = "dataset_train/Yo"
cantidad_fotos = 120 # Un poco más para tener variedad
# ---------------------

if not os.path.exists(nombre_carpeta):
    os.makedirs(nombre_carpeta)

# Iniciar la cámara que SÍ te funciona (DroidCam)
cap = cv2.VideoCapture(1, cv2.CAP_DSHOW)
if not cap.isOpened(): cap = cv2.VideoCapture(0, cv2.CAP_DSHOW)

# Configurar resolución para que coincida con el reconocimiento
cap.set(cv2.CAP_PROP_FRAME_WIDTH, 1280)
cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 720)

face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

count = 0
print("--- INSTRUCCIONES PARA LA SESIÓN DE FOTOS ---")
print("1. Mueve la cabeza despacio (izquierda, derecha, arriba, abajo).")
print("2. Acerca y aleja la cara de la cámara.")
print("3. Cambia tu expresión (sonríe, serio, boca abierta).")
print("4. Si puedes, prende y apaga la luz de tu cuarto.")
print("---------------------------------------------")
print("Presiona ENTER para empezar...")
input()

while True:
    ret, frame = cap.read()
    if not ret: break
    
    frame = cv2.flip(frame, 1)
    display = frame.copy()
    
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    for (x, y, w, h) in faces:
        # Dibujar cuadro
        cv2.rectangle(display, (x, y), (x+w, y+h), (0, 255, 0), 2)
        
        # Guardar foto (Recortada y lista)
        # IMPORTANTE: Dejamos un margen pequeño
        rostro = frame[y:y+h, x:x+w]
        try:
            rostro = cv2.resize(rostro, (160, 160)) # Tamaño MobileNet
            
            # Guardamos cada 5 cuadros para que no sean idénticas
            if int(time.time() * 10) % 3 == 0: 
                cv2.imwrite(f"{nombre_carpeta}/yo_nativa_{count}.jpg", rostro)
                count += 1
                print(f"📸 Foto {count}/{cantidad_fotos}")
        except:
            pass

    cv2.putText(display, f"FOTOS: {count}/{cantidad_fotos}", (10, 50), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)
    cv2.imshow('Captura Nativa - MUEVETE!', display)
    
    if count >= cantidad_fotos or (cv2.waitKey(1) & 0xFF == ord('q')):
        break

cap.release()
cv2.destroyAllWindows()
print("¡Listo! Fotos capturadas.")