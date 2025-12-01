import cv2

# Intenta con 0, si falla cambia a 1
INDICE_CAMARA = 0 

print(f"Intentando abrir cámara {INDICE_CAMARA}...")

# Usamos CAP_DSHOW para Windows
cap = cv2.VideoCapture(INDICE_CAMARA, cv2.CAP_DSHOW)

# Forzamos una resolución baja y segura para probar
cap.set(cv2.CAP_PROP_FRAME_WIDTH, 640)
cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 480)

if not cap.isOpened():
    print("❌ Error: No se pudo abrir la cámara.")
    exit()

print("✅ Cámara abierta. Presiona 'q' para salir.")

while True:
    ret, frame = cap.read()
    if not ret:
        print("Error leyendo frame")
        break

    cv2.imshow('Prueba Simple', frame)
    
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()