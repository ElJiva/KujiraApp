# 📚 ComicsAPI

API REST construida con Go + Fiber y MongoDB, completamente dockerizada para desarrollo.

## 🚀 Inicio Rápido

### Requisitos
- Docker
- Docker Compose

**Nota:** No necesitas tener Go ni MongoDB instalados localmente.

### Instalación

1. Clona el repositorio:
```bash
git clone <url-del-repo>
cd comicsApi
```

2. Genera las dependencias de Go:
```bash
docker-compose run --rm api go mod tidy
```

3. Levanta los contenedores:
```bash
docker-compose up --build
```

4. La API estará disponible en: `http://localhost:3000`

## 📋 Endpoints Disponibles

- `GET /` - Mensaje de bienvenida y estado de la API
- `GET /health` - Verifica la conexión con MongoDB y lista las bases de datos

### Ejemplo de uso:

```bash
# Probar endpoint básico
curl http://localhost:3000/

# Probar conexión con MongoDB
curl http://localhost:3000/health
```

## 🔧 Configuración

Las variables de entorno se configuran en el archivo `.env`:

```env
# MongoDB
MONGO_USER=admin
MONGO_PASSWORD=admin123
MONGO_DATABASE=kujiradb
MONGO_PORT=27017
MONGO_URI=mongodb://admin:admin123@mongodb:27017/kujiradb?authSource=admin

# API
API_PORT=3000
```

## 🛠️ Desarrollo

### Hot Reload
El proyecto usa **Air** para recargar automáticamente los cambios. Solo guarda tus archivos `.go` y la aplicación se reconstruirá automáticamente.

### Ver logs
```bash
docker-compose logs -f api
```

### Detener los contenedores
```bash
docker-compose down
```

### Detener y eliminar volúmenes (limpia la BD)
```bash
docker-compose down -v
```

### Regenerar dependencias
Si agregas nuevas dependencias en `go.mod`:
```bash
docker-compose run --rm api go mod tidy
```

## 📦 Estructura del Proyecto

```
kujiraApp/
├── .air.toml           # Configuración de Air (hot-reload)
├── .env                # Variables de entorno
├── .gitignore          # Archivos ignorados por Git
├── docker-compose.yml  # Configuración de Docker Compose
├── Dockerfile          # Imagen de Docker para la API
├── go.mod              # Dependencias de Go
├── go.sum              # Checksums de dependencias
├── main.go             # Punto de entrada de la aplicación
└── README.md           # Este archivo
```

## 🗄️ Base de Datos

MongoDB está disponible en:
- **Host:** `localhost`
- **Puerto:** `27017`
- **Usuario:** `admin`
- **Password:** `admin123`
- **Base de datos:** `kujiradb`

Puedes conectarte usando MongoDB Compass o cualquier cliente de MongoDB.

## 📝 Notas

- Los datos de MongoDB se persisten en un volumen de Docker llamado `mongodb_data`
- La carpeta `tmp/` es creada por Air y está en `.gitignore`
- El archivo `.env` está# 📚 ComicsAPI