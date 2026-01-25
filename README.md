# Pokedex

Proyecto academico de 2º de DAW (modulo Desarrollo de aplicaciones web en entorno cliente). Aplicacion web con Spring Boot y Thymeleaf para practicar el consumo de una API externa gratuita (PokeAPI).

![Vista previa](docs/preview.png)

> Nota: guarda la imagen adjunta como `docs/preview.png` para que se muestre aqui.

## Descripcion
Aplicacion web MVC que permite buscar Pokemon por nombre o id, listar los 20 primeros y consultar detalles (imagen, tipos, altura y peso). Los datos se obtienen en tiempo real desde PokeAPI.

## Funcionalidades
- Busqueda de Pokemon por nombre o id desde un formulario.
- Vista de detalles con imagen, tipos, altura y peso.
- Listado de los 20 primeros Pokemon con enlace a detalles.
- Validacion de formulario y mensajes de error.
- Interfaz web con Thymeleaf y estilos propios.

## Tecnologias
- Java 25
- Spring Boot 4.0.2
- Spring WebMVC
- Thymeleaf
- Jakarta Validation
- Lombok
- Maven (wrapper incluido)
- PokeAPI

## Requisitos
- Java 25 instalado y `JAVA_HOME` configurado.
- Acceso a internet para consumir PokeAPI.
- Maven o el wrapper incluido (`mvnw`).

## Instalacion y ejecucion
1. Clona el repositorio.
2. Ejecuta la aplicacion:

Windows:
```sh
mvnw.cmd spring-boot:run
```

macOS/Linux:
```sh
./mvnw spring-boot:run
```

3. Abre `http://localhost:8080/`.

> Si tienes Maven instalado, tambien puedes usar `mvn spring-boot:run`.

## Rutas principales
| Metodo | Ruta | Descripcion |
| --- | --- | --- |
| GET | / | Portada |
| GET | /buscar | Formulario de busqueda |
| POST | /buscar | Procesa la busqueda y redirige a detalles |
| GET | /detalles/{nombreOId} | Detalles de un Pokemon |
| GET | /listado | Listado de los 20 primeros Pokemon |

## Estructura del proyecto
- `src/main/java/com/actividad/pokedex/controller`: controladores web.
- `src/main/java/com/actividad/pokedex/service`: logica de negocio y mapeo.
- `src/main/java/com/actividad/pokedex/client`: cliente REST para PokeAPI.
- `src/main/java/com/actividad/pokedex/dto`: DTOs de request/response.
- `src/main/java/com/actividad/pokedex/model`: modelos de dominio.
- `src/main/resources/templates`: vistas Thymeleaf.
- `src/main/resources/static/css`: estilos.

## Validacion y manejo de errores
- El formulario valida que `nombreOId` no este vacio.
- Si la PokeAPI no encuentra resultados, se muestra un mensaje al usuario.

## API externa
- PokeAPI: https://pokeapi.co/
- Endpoints usados: `/api/v2/pokemon/{nombreOId}` y `/api/v2/pokemon?limit=20`.
- No requiere autenticacion.


## Autor y contexto academico
- Javie Garcia Pons
- 2º DAW
- Modulo: Desarrollo de aplicaciones web en entorno cliente
