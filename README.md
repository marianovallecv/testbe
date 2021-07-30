# Proyecto Test (backend)

Este repositorio de Git contiene el codigo fuente de la API testbe, ademas contiene informacion acerca del uso

# Concideraciones:
Antes de importar este proyecto, tener en cuenta que solo esta configurado para correr localmente.
Se usaran dos proyectos:

* **testbe: Para ejecutar el backend**:
* testfe: Para ejecutar el frontend

En este apartado se trataran las especificaciones del backend.

### Pre-requisitos
### Instalación
* Tener instalado java 16

##### Construyendo el proyecto
Podemos invocar el siguiente comando desde la raíz del repositorio si queremos construir el repositorio completo con solo las Pruebas unitarias habilitadas: 

```
./gradlew build --refresh-dependencies
```

##### Construyendo un modulo simple
Para construir un módulo específico, ejecute el comando: `gradle install` en el directorio del módulo

##### Corriendo el moduo spring boot
Para ejecutar un módulo Spring Boot, ejecute el comando: `spring-boot:run` en el directorio del módulo.

Creacion de la base de datos:
====================
Para crear la base de datos debe ejecutar el dum (/testbe/src/main/resources/db/dump.sql) en una base de datos MySQL.


##### Trabajando con el IDE
Este repositorio contiene solamente un móduls.
Simplemente puede importar ese módulo en particular en Eclipse o IntelliJ.

##### Ejecucion de pruebas
El comando `gradle install` desde dentro de un módulo ejecutará las pruebas unitarias en ese módulo.
Para los módulos Spring, esto también ejecutará el `SpringContextTest` si está presente.

Para ejecutar las pruebas de integración, use el comando:

```
gradle install -Pintegration-lite-first
```



Los proyectos se dividen ampliamente en 3 listas: primero, segundo y pesado.



## Autores

* **Mariano Valle** - *Trabajo Inicial, Documentacion* - [marianovallecv](https://github.com/marianovallecv)

Tambien puedes mirar la lista de todos los [contribuyentes](https://github.com/your/project/contributors) quienes han participado en este proyecto.




### collection de Postman:
- [Acceso a la API Test (Postman)](https://www.getpostman.com/collections/976810fe8d0e2639dc54)



_texto_ : italic

**text** : bold
[Acces]