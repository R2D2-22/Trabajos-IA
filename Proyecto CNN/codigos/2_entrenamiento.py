import tensorflow as tf
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.applications import MobileNetV2
from tensorflow.keras.layers import Dense, GlobalAveragePooling2D, Dropout, BatchNormalization
from tensorflow.keras.models import Model
from tensorflow.keras.optimizers import Adam
from tensorflow.keras.applications.mobilenet_v2 import preprocess_input
import os

# --- CONFIGURACIÓN ---
dataset_path = "dataset_train"
img_size = (160, 160)
batch_size = 32
epochs_fase1 = 15 # Calentamiento
epochs_fase2 = 15 # Afinamiento fino (Fine Tuning)
# ---------------------

# 1. GENERADORES (OJO: Sin rescale manual, solo preprocess_input)
train_datagen = ImageDataGenerator(
    preprocessing_function=preprocess_input, # Esto convierte a rango -1 a 1 automáticamente
    rotation_range=20,
    width_shift_range=0.2,
    height_shift_range=0.2,
    shear_range=0.2,
    zoom_range=0.2,
    horizontal_flip=True,
    fill_mode='nearest',
    validation_split=0.2
)

print("Cargando datos...")
train_generator = train_datagen.flow_from_directory(
    dataset_path,
    target_size=img_size,
    batch_size=batch_size,
    class_mode='categorical',
    subset='training'
)

val_generator = train_datagen.flow_from_directory(
    dataset_path,
    target_size=img_size,
    batch_size=batch_size,
    class_mode='categorical',
    subset='validation'
)

class_names = list(train_generator.class_indices.keys())
if not os.path.exists('modelos_pro'): os.makedirs('modelos_pro')
with open('modelos_pro/clases.txt', 'w') as f:
    f.write(str(class_names))

# 2. CONSTRUIR EL MODELO ROBUSTO
base_model = MobileNetV2(weights='imagenet', include_top=False, input_shape=(160, 160, 3))

# Fase 1: Congelar TODO el cerebro base
base_model.trainable = False 

x = base_model.output
x = GlobalAveragePooling2D()(x)
x = Dense(256, activation='relu')(x) # Más neuronas aquí
x = BatchNormalization()(x)          # Estabiliza el aprendizaje
x = Dropout(0.5)(x)                  # Evita memorizar
predictions = Dense(len(class_names), activation='softmax')(x)

model = Model(inputs=base_model.input, outputs=predictions)

# 3. ENTRENAMIENTO FASE 1 (Solo la cabeza)
print("\n--- FASE 1: Entrenando cabecera (Cerebro congelado) ---")
model.compile(optimizer=Adam(learning_rate=0.001), loss='categorical_crossentropy', metrics=['accuracy'])
model.fit(train_generator, epochs=epochs_fase1, validation_data=val_generator)

# 4. FINE TUNING (El secreto del éxito 🚀)
print("\n--- FASE 2: Descongelando últimas capas (Fine Tuning) ---")
base_model.trainable = True # Descongelamos

# Congelamos las primeras 100 capas, dejamos libres las últimas para que aprendan caras
for layer in base_model.layers[:100]:
    layer.trainable = False

# Re-compilamos con un learning rate MUY BAJO para no romper lo aprendido
model.compile(optimizer=Adam(learning_rate=0.0001), loss='categorical_crossentropy', metrics=['accuracy'])

history_fine = model.fit(train_generator, epochs=epochs_fase2, validation_data=val_generator)

# 5. GUARDAR
model.save('modelos_pro/mobilenet_finetuned.h5')
print("¡Modelo FINETUNED guardado!")