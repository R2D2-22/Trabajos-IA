import cv2

print("--- ESCANEANDO CÁMARAS DISPONIBLES ---")

# Vamos a probar los puertos del 0 al 4
for i in range(5):
    print(f"Probando índice {i}...", end=" ")
    
    # Intenta abrir la cámara en ese puerto
    # cv2.CAP_DSHOW ayuda a que Windows responda más rápido
    cap = cv2.VideoCapture(i, cv2.CAP_DSHOW) 
    
    if not cap.isOpened():
        print("❌ Falló al abrir.")
    else:
        ret, frame = cap.read()
        if ret:
            print(f"\n✅ ¡ÉXITO! CÁMARA ENCONTRADA EN ÍNDICE: {i}")
            print(f"   Resolución: {frame.shape[1]}x{frame.shape[0]}")
            # Guardamos una foto de prueba para que veas si es la correcta
            cv2.imwrite(f"prueba_camara_{i}.jpg", frame)
            print(f"   (Foto guardada como prueba_camara_{i}.jpg)")
        else:
            print("⚠️ Abre, pero no da imagen (Pantalla negra).")
    
    cap.release()

print("\n--- FIN DEL ESCANEO ---")