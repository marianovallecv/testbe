# Proyecto Test (backend)
***
Este repositorio de Git contiene el código fuente de la API **testbe**, ademas contiene informacion acerca de su uso.


## Contenidos
* [1. Considerations](#Considerations)
* [2.  Prerequisites](#Prerequisites)
* [3. Instalation](#Instalation)
* [3.1 Sources](#Sources)
* [3.2 Build](#Build)
* [3.3 Database](#Database)
* [4. IDE](#IDE)
* [5. Test](#Test)
* [6. Technologies](#Technologies)
* [7. API](#API)
* [8. Author](#Author)

## Considerations
Antes de importar este proyecto, tener en cuenta que solo esta configurado para correr localmente.
Este proyecto está en un estado de prueba, actualmente en desarrollo.
Se usaran dos proyectos:

* **testbe:** Para ejecutar el backend:
* **testfe:** Para ejecutar el frontend

En este apartado se trataran las especificaciones del **backend**.

## Prerequisites
- Tener instalado JRE 16.
- Configurar las variables de entorno user **jdk-16.0.1**.
- En caso de querer desarrollar funcionalidades contar con un IDE como por ejemplo Eclipse.

## Instalation

### Sources
* Descargar el proyeco desde el [repositorio GITHUB](https://github.com/marianovallecv/testbe)

### Build
Podemos invocar el siguiente comando desde la raíz del repositorio si queremos construir el repositorio completo con solo las Pruebas unitarias habilitadas: 

```
gradle build --refresh-dependencies
```

### Construyendo un modulo simple
Para construir un módulo específico, ejecute el comando: `gradle install` en el directorio del módulo

### Corriendo el moduo spring boot
Para ejecutar un módulo Spring Boot, ejecute el comando: `spring-boot:run` en el directorio del módulo.

### Database

#### Ejecutar dump
Para crear la base de datos debe ejecutar el dum `"/testbe/src/main/resources/db/dump.sql"` en una base de datos MySQL.

## IDE
Este repositorio contiene solamente un móduls.
Simplemente puede importar ese módulo en particular en Eclipse o IntelliJ.

## Test
El comando `gradle install` desde dentro de un módulo ejecutará las pruebas unitarias en ese módulo.
Para los módulos Spring, esto también ejecutará el `SpringContextTest` si está presente.

## Technologies
Listado de las tecnologías usadas para este proyecto:
* [Java](https://java.com): Version 16
* [Spring Boot](https://spring.io/projects/spring-boot): Version 2.5.3
* [Gradle](https://gradle.com): Version 7.1.1
* [MySQL](https://www.mysql.com): Version 8.0.25
* [Mockito](https://site.mockito.org)
* [Postman](https://www.postman.com)

## API

### Login:
* Puede usar el usuario "admin (ROLE_ADMIN)" o "user (ROLE_USER)" con el mismo pass.
Generar el tocken de acceso y reemplazarlo en las peticiones ([TOKEN]) a la API.

```
curl --location --request GET 'http://localhost:8020/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
"userName": "admin",
"password": "Pipo1234"
}'
```

### Candidates:

#### Get one:
Reemplazar [ID] por el id deseado.

```
curl --location --request GET 'http://localhost:8020/candidates/[ID]' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

#### Get pages:

```
curl --location --request GET 'http://localhost:8020/candidates/' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

#### Get pages filter:
Reemplazar [DATA] por el documento o nombre t apellido.

```
curl --location --request GET 'http://127.0.0.1:8020/candidates/filter/[DATA]' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

#### Get by email:
Reemplazar [EMAIL] por el email deseado.

```
curl --location --request GET 'http://localhost:8020/candidates/email/[EMAIL]' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

#### Get by document:
Reemplazar [DOCUMENT] por el documento deseado.

```
curl --location --request GET 'http://localhost:8020/candidates/document/[DOCUMENT]' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

#### Get by fullName:
Reemplazar [FULL_NAME] por el nombre y apellido deseado.

```
curl --location --request GET 'http://localhost:8020/candidates/fullName/[FULL_NAME]' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

#### Get by document:
Reemplazar [FULL_NAME] por el nombre y apellido deseado.

```
curl --location --request GET 'http://localhost:8020/candidates/fullName/[FULL_NAME]' \
--header 'Authorization: Bearer Bearer [TOKEN]'
```

#### Post one:
Reemplazar "[FULL_NAME]", [DOCUMENT], "[BIRTH]", "[ADDRESS]", "[PHONE]", "[EMAIL]" por los valores deseados.

```
curl --location --request POST 'http://localhost:8020/candidates/' \
--header 'Authorization: Bearer Bearer [TOKEN]' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=B643E07BDD945A65EAE810C244276DA8' \
--data-raw '{
    "fullName": "[FULL_NAME]",
    "document": [DOCUMENT],
    "birth": "[BIRTH]",
    "address": "[ADDRESS]",
    "phone": "[PHONE]",
    "email": "[EMAIL]"
}'
```

#### Put one:
Reemplazar [ID] por el id a modificar y "[FULL_NAME]", [DOCUMENT], "[BIRTH]", "[ADDRESS]", "[PHONE]", "[EMAIL]" por los valores deseados.

```
curl --location --request PUT 'http://localhost:8020/candidates/[ID]' \
--header 'Authorization: Bearer Bearer [TOKEN]' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=B643E07BDD945A65EAE810C244276DA8' \
--data-raw '{
    "fullName": "[FULL_NAME]",
    "document": [DOCUMENT],
    "birth": "[BIRTH]",
    "address": "[ADDRESS]",
    "phone": "[PHONE]",
    "email": "[EMAIL]"
}'
```

#### Delete one:
Reemplazar [ID] por el id a eliminar.

```
curl --location --request DELETE 'http://localhost:8020/candidates/[ID]' \
--header 'Authorization: Bearer Bearer [TOKEN]' \
--header 'Cookie: JSESSIONID=B643E07BDD945A65EAE810C244276DA8'
```

### collection de Postman:
- [Acceso a la API Test (Postman)](https://www.getpostman.com/collections/33fa95585b352209f046)

## Author
* **Mariano Valle** - *Trabajo Inicial, Documentacion* - [marianovallecv](https://github.com/marianovallecv/testbe/tree/master/doc)

Tambien puedes mirar la lista de todos mis [proyectos](https://github.com/marianovallecv).
