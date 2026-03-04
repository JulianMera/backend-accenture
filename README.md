# Franchise API – Backend Accenture

API REST para gestión de **franquicias**, **sucursales** y **productos**. Incluye documentación OpenAPI (Swagger UI), tests unitarios y despliegue con Docker.

---

## Requisitos previos

| Entorno      | Necesario |
|-------------|-----------|
| **Local**   | Java 17+, Maven 3.6+, MySQL 8 (opcional si usas solo Docker) |
| **Docker** | Docker y Docker Compose |

---

## 1. Despliegue en entorno local

### 1.1 Clonar / abrir el proyecto

```bash
cd backend-accenture
```

### 1.2 Base de datos (MySQL)

Tienes dos opciones:

**Opción A – MySQL ya instalado en tu máquina**

- Crea la base de datos (si no existe):

  ```sql
  CREATE DATABASE franchise_db;
  ```

- Por defecto la aplicación usa:
  - **URL:** `jdbc:mysql://localhost:3306/franchise_db`
  - **Usuario:** `root`
  - **Contraseña:** `root`

- Si tu MySQL usa otro puerto, usuario o contraseña, define variables de entorno antes de arrancar (ver sección 1.4).

**Opción B – Solo MySQL en Docker**

```bash
docker run -d --name franchise-mysql \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=franchise_db \
  -p 3306:3306 \
  mysql:latest
```

Luego arranca la API con Maven como en 1.3 (con `localhost:3306` por defecto).

### 1.3 Compilar y ejecutar la aplicación

```bash
# Compilar y empaquetar (incluye tests)
mvn clean package

# Ejecutar la aplicación
mvn spring-boot:run
```

O ejecutando el JAR directamente:

```bash
mvn clean package -DskipTests
java -jar target/backend-accenture-0.0.1-SNAPSHOT.jar
```

La API quedará disponible en **http://localhost:8080**.

### 1.4 Variables de entorno (opcional)

Si tu MySQL no es `localhost:3306` con usuario/contraseña `root`/`root`, puedes configurar:

| Variable                    | Descripción              | Valor por defecto                          |
|----------------------------|--------------------------|--------------------------------------------|
| `SPRING_DATASOURCE_URL`    | URL JDBC de MySQL        | `jdbc:mysql://localhost:3306/franchise_db`  |
| `SPRING_DATASOURCE_USERNAME` | Usuario de la base     | `root`                                     |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña             | `root`                                     |

Ejemplo (Linux/macOS):

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3307/franchise_db
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=root
mvn spring-boot:run
```

En Windows (PowerShell):

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3307/franchise_db"
$env:SPRING_DATASOURCE_USERNAME="root"
$env:SPRING_DATASOURCE_PASSWORD="root"
mvn spring-boot:run
```

---

## 2. Despliegue con Docker

El proyecto incluye **Dockerfile** y **Docker Compose** para levantar la API y MySQL en contenedores.

### 2.1 Construir el JAR

El contenedor de la API usa el JAR generado por Maven, así que primero hay que compilar:

```bash
mvn clean package
```

(Si quieres omitir tests: `mvn clean package -DskipTests`.)

### 2.2 Levantar los servicios con Docker Compose

En la raíz del proyecto (donde está `compose.yaml`):

```bash
docker compose up -d
```

Esto hará:

- **MySQL** en el contenedor `franchise-mysql`, puerto **3307** en el host (para evitar conflicto con un MySQL local en 3306).
- **API** en el contenedor `franchise-api`, puerto **8080** en el host. La API se conecta a MySQL por la red interna de Docker (`mysql:3306`).

### 2.3 Comprobar que estén en ejecución

```bash
docker compose ps
```

Deberías ver los dos servicios en estado “running”.

### 2.4 Ver logs (opcional)

```bash
# Todos los servicios
docker compose logs -f

# Solo la API
docker compose logs -f franchise-api

# Solo MySQL
docker compose logs -f franchise-mysql
```

### 2.5 Detener y eliminar contenedores

```bash
docker compose down
```

Los datos de MySQL se conservan en un volumen. Para borrar también el volumen:

```bash
docker compose down -v
```

---

## 3. Acceder a Swagger UI (documentación de la API)

Con la aplicación en marcha (local o Docker), la documentación interactiva está en:

| Recurso        | URL |
|----------------|-----|
| **Swagger UI** (interfaz para probar endpoints) | **http://localhost:8080/swagger-ui.html** |
| **OpenAPI JSON**                               | http://localhost:8080/v3/api-docs          |

### Pasos rápidos

1. Arranca la API (local con `mvn spring-boot:run` o Docker con `docker compose up -d`).
2. Abre en el navegador: **http://localhost:8080/swagger-ui.html**.
3. En Swagger UI verás los grupos **Franquicias**, **Sucursales** y **Productos** y podrás ejecutar cada endpoint desde la propia página.

---

## 4. Resumen de endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST   | `/franchises` | Crear franquicia |
| GET    | `/franchises/{franchiseId}/top-products` | Top productos por franquicia |
| PUT    | `/franchises/{franchiseId}` | Actualizar nombre de franquicia |
| POST   | `/branches/franchise/{franchiseId}` | Crear sucursal |
| PUT    | `/branches/{branchId}` | Actualizar nombre de sucursal |
| POST   | `/products/branch/{branchId}` | Crear producto |
| DELETE | `/products/{productId}` | Eliminar producto |
| PUT    | `/products/{productId}/stock` | Actualizar stock |
| PUT    | `/products/{productId}/name` | Actualizar nombre de producto |

---

## 5. Tests

```bash
# Ejecutar todos los tests (usa H2 en memoria, no requiere MySQL)
mvn test
```

---

## 6. Estructura del proyecto

```
backend-accenture/
├── compose.yaml          # Docker Compose (API + MySQL)
├── Dockerfile            # Imagen de la API
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/.../backend_accenture/
    │   │   ├── config/       # Configuración (OpenAPI, etc.)
    │   │   ├── controller/
    │   │   ├── dto/
    │   │   ├── entity/
    │   │   ├── exception/
    │   │   ├── repository/
    │   │   └── service/
    │   └── resources/
    │       └── application.properties
    └── test/
        └── java/.../backend_accenture/
            ├── controller/
            └── service/
```

---

## 7. Tecnologías

- **Java 17** · **Spring Boot 4** · **Spring Data JPA** · **MySQL** · **SpringDoc OpenAPI (Swagger UI)** · **Docker / Docker Compose**
