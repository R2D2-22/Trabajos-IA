import cv2
import os
import glob

# --- CONFIGURACIÓN CORREGIDA ---
# Como ejecutas desde la carpeta principal, las rutas son simples:
input_folder = "dataset"        
output_folder = "dataset_train" 
# ---------------------

if not os.path.exists(input_folder):
    print(f"❌ ERROR: No encuentro la carpeta '{input_folder}'")
    exit()

if not os.path.exists(output_folder):
    os.makedirs(output_folder)
    print(f"Carpet '{output_folder}' creada.")
carpetas = os.listdir(input_folder)
print(f"Buscando imágenes en: {os.path.abspath(input_folder)}")
print(f"Encontré estas carpetas: {carpetas}")

total = 0
for nombre in carpetas:
    ruta_entrada = os.path.join(input_folder, nombre)
    if not os.path.isdir(ruta_entrada): continue

    # Crear subcarpeta en dataset_train
    ruta_salida = os.path.join(output_folder, nombre)
    if not os.path.exists(ruta_salida):
        os.makedirs(ruta_salida)
    
    # Buscar imágenes (jpg, png, jpeg)
    archivos = glob.glob(f"{ruta_entrada}/*")
    
    count = 0
    for archivo in archivos:
        try:
            img = cv2.imread(archivo)
            if img is None: continue
            
            # REDIMENSIONAR A 150x150
            img = cv2.resize(img, (160, 160))
            
            cv2.imwrite(f"{ruta_salida}/img_{count}.jpg", img)
            count += 1
            total += 1
        except:
            pass
    print(f"✅ {nombre}: {count} fotos procesadas.")

print(f"\n--- LISTO ---")
print(f"Se creó la carpeta 'dataset_train' con {total} imágenes.")