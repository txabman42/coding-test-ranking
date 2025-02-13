# Reto: Servicio para gestión de calidad de los anuncios

## Tech stack

To carry out this technical challenge, the following technologies have been used:
- Java 15
- Spring Boot 2.4.4
- Webflux
- Spock 2.0

## Api documentation

API is documented with Swagger following the OpenAPI 3 specifications:

- OpenAPI descriptions: http://localhost:8080/api-docs
- Swagger definition: http://localhost:8080/swagger-ui.html

## Build

In order to build the service, from root folder it has to be done one of the following steps:

- **Docker**
  
  This is the easiest way to run the application. Thanks to the multi-stage Dockerfile, no other technology will
  be required to compile, package and run the application. In the first stages, the program will be packaged
  using the maven image [maven:3.6.3-openjdk-15](https://hub.docker.com/_/maven). In the last stage, the application
  will be executed on the image [openjdk:15-jdk-slim](https://hub.docker.com/_/openjdk).

  - **Build image**:
    ```
    docker build -t idealista-score-challenge:latest .
    ```
  - **Docker compose**: Go to the following path ```./docs``` and run:
    
    ```
    docker-compose up -d
    ```

- **Maven**
  
  Run the following command to compile the service
  ```
  mvn clean compile
  ```
  To run the previously compiled JAR run the [Maven Spring Boot plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/)
  goal:
  ```
  mvn spring-boor:run
  ```

## Testing

[Spock](https://spockframework.org/spock/docs/1.3/all_in_one.html) has been used for unit and integration tests.
Some of its advantages are:

- Easy of use via the Given/When/Then approach
- Easily write one test with multiple cases
- Verbose output for test failures
- Built-in mocking library

The execution of the unit tests is under the responsibility of the
[Surefire plugin](https://maven.apache.org/surefire/maven-surefire-plugin/), while the integration tests will be
executed thanks to the [Failsafe plugin](https://maven.apache.org/surefire/maven-failsafe-plugin/).
Since Spock is based on Groovy, the [Gmavenplus plugin](https://groovy.github.io/GMavenPlus/) has been used for
compiling the tests. You can run the tests with the following commands:

- Run unit tests: ```mvn test```

- Run unit and integration tests: ```mvn verify```

## Security

Basic authentication mechanism has been implemented. This security filter has been added at the web level,
requiring authentication to access any ```/api/private``` endpoint. In this case, no specific role is requested,
only that the user is authenticated. Since this is an exercise with a reduced scope, the following credentials have
been loaded directly into memory:

- Username: idealistaUser
- Password: idealistaPassword

These credentials are configurable through the following configuration properties in the application.yml:
```
idealista:
  security:
    username:
    password:
    role
```
or defining the following environment variables:

- APP_USERNAME
- APP_PASSWORD
- APP_ROLE

### Enunciado

[![Build Status](https://travis-ci.org/idealista/coding-test-ranking.svg?branch=master)](https://travis-ci.org/idealista/coding-test-ranking)

Este repositorio contiene un API parcialmente desarrollada para desarrollar un servicio que se encargue de medir la calidad de los anuncios. Tu objetivo será implementar las historias de usuario que se describen más adelante.

Los supuestos están basados en un hipotético *equipo de gestión de calidad de los anuncios*, que demanda una serie de verificaciones automáticas para clasificar los anuncios en base a una serie de características concretas.

## Historias de usuario

* Yo, como encargado del equipo de gestión de calidad de los anuncios quiero asignar una puntuación a un anuncio para que los usuarios de idealista puedan ordenar anuncios de más completos a menos completos. La puntuación del anuncio es un valor entre 0 y 100 que se calcula teniendo encuenta las siguientes reglas:
  * Si el anuncio no tiene ninguna foto se restan 10 puntos. Cada foto que tenga el anuncio proporciona 20 puntos si es una foto de alta resolución (HD) o 10 si no lo es.
  * Que el anuncio tenga un texto descriptivo suma 5 puntos.
  * El tamaño de la descripción también proporciona puntos cuando el anuncio es sobre un piso o sobre un chalet. En el caso de los pisos, la descripción aporta 10 puntos si tiene entre 20 y 49 palabras o 30 puntos si tiene 50 o mas palabras. En el caso de los chalets, si tiene mas de 50 palabras, añade 20 puntos.
  * Que las siguientes palabras aparezcan en la descripción añaden 5 puntos cada una: Luminoso, Nuevo, Céntrico, Reformado, Ático.
  * Que el anuncio esté completo también aporta puntos. Para considerar un anuncio completo este tiene que tener descripción, al menos una foto y los datos particulares de cada tipología, esto es, en el caso de los pisos tiene que tener también tamaño de vivienda, en el de los chalets, tamaño de vivienda y de jardín. Además, excepcionalmente, en los garajes no es necesario que el anuncio tenga descripción. Si el anuncio tiene todos los datos anteriores, proporciona otros 40 puntos.

* Yo como encargado de calidad quiero que los usuarios no vean anuncios irrelevantes para que el usuario siempre vea contenido de calidad en idealista. Un anuncio se considera irrelevante si tiene una puntación inferior a 40 puntos.

* Yo como encargado de calidad quiero poder ver los anuncios irrelevantes y desde que fecha lo son para medir la calidad media del contenido del portal.

* Yo como usuario de idealista quiero poder ver los anuncios ordenados de mejor a peor para encontrar fácilmente mi vivienda.

## Consideraciones importantes

En este proyecto te proporcionamos un pequeño *esqueleto* escrito en Java usando [Spring Boot](https://spring.io/projects/spring-boot).

En dicho *esqueleto* hemos dejado para que completes un [Controller](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Controller.html) y un [Repository](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Repository.html) en memoria. Puedes crear las clases y métodos que consideres necesarios.

Podrás ejecutar el proyecto usando Maven ejecutando el siguiente comando en la carpeta donde esté el fichero `pom.xml`:

```bash
$ mvn spring-boot:run
```

**La persistencia de datos no forma parte del objetivo del reto**. Si no vas a usar el esqueleto que te proporcionamos, te sugerimos que la simplifiques tanto como puedas (con una base de datos embebida, "persistiendo" los objetos en memoria, usando un fichero...). **El diseño de una interfaz gráfica tampoco** forma parte del alcance del reto, por tanto no es necesario que la implementes.

**Nota:** No estás obligado a usar el proyecto proporcionado. Si lo prefieres, puedes usar cualquier otro lenguaje, framework y/o librería. Incluso puedes prescindir de estos últimos si consideras que no son necesarios. A lo que más importancia damos es a tener un código limpio y de calidad.

### Requisitos mínimos

A continuación se enumeran los requisitos mínimos para ejecutar el proyecto:

* Java 1.8
* Apache Maven 3.6.x

Otras versiones pueden funcionar, pero no han sido probadas y pueden presentar errores.

## Criterios de aceptación

* El código debe compilar y ser ejecutable :dancer:

* Debes proporcionar 3 endpoints: Uno para calcular la puntuación de todos los anuncios, otro para listar los anuncios para un usuario de idealista y otro para listar los anuncios para el responsable de del departamento de gestión de calidad.
