# API REST de Gestión de Préstamos Personales

Este proyecto implementa una API REST sencilla para la gestión de solicitudes de préstamos personales, desarrollada con **Spring Boot**.  
Permite a los usuarios enviar solicitudes de préstamo y a los gestores revisarlas, consultarlas y modificar su estado siguiendo un flujo definido.

---

## 1. Instrucciones para ejecutar el proyecto

### 1.1 Requisitos previos
- Java 17 o superior
- Maven 3.x
- IDE recomendado: IntelliJ IDEA, Eclipse o VS Code (Se ha implementado en Eclipse)
- Spring Boot 4.0.1
- Puerto por defecto: `8080`

### 1.2 Dependencias principales de Spring Boot
- **spring-boot-starter-web** → Para crear la API REST y exponer los endpoints
- **spring-boot-starter-data-jpa** → Para manejar la persistencia de datos con JPA
- **h2** → Base de datos en memoria para pruebas rápidas

### 1.3 Pasos para ejecutar
  1. Clonar o descargar el proyecto
  2. Abrir el proyecto en el IDE
  3. Ejecutar la clase principal:

```java
PrestamoApplication.java

### 1.4 Iniciar la aplicación
 
http://localhost:8080


-------

## 2. Uso de Postman para probar la API y Base de Datos en memoria H2 Console

### 2.1 Uso de Postman
 - Para probar el funcionamiento de la API se ha procedido a descargar la herrmanienta POSTMAN para analizar de manera mas fluida los endpoints POST, GET y PATCH

### 2.2 Pasos Básicos en Postman
1. Abrir Postman
2. Crear una nueva request
3. Seleccionar el método HTTP (POST, GET, PATCH)
4. Introducir la URL del endpoint
5. En requests con body:
    - Seleccionar Body → raw → JSON
    - Introducir el JSON correspondiente

### 2.3 H2 Console
 - Este proyecto utiliza H2, una base de datos en memoria, para simplificar la ejecución y comprobación de los datos

### 2.4 Pasos para ejecutar H2 Console
  1.Configuración del fichero application.properties (ya configurado en el proyecto)
  2. Acceso a la consola H2: 
	· URL: http://localhost:8080/h2-console
	· JDBC URL: jdbc:h2:mem:testdb
	· User: sa
	· Password: (vacío)


------

## 3.Roles del sistema
  USUARIO
   - Puede crear solicitudes y consultar únicamente las suyas.
  GESTOR
  Controla estados y consultas:
   - Puede ver todas las solicitudes
   - Puede consultar una solicitud por ID
   - Puede cambiar el estado de una solicitud
  SISTEMA
   - Puede consultar todas las solicitudes (simulación de procesos internos).
 

-----
## 4.Endpoints de la API

	1. Enviar solicitud:
	· POST http://localhost:8080/api/prestamos y 
	· Body (JSON)	  
	  {
		"nombreSolicitante": "Juan Pérez",
  		"DNI": "12345678A",
  		"importe": 5000,
  		"divisa": "EUR"
	  }

	2. Listar todas las solicitudes existentes: 
	· GET http://localhost:8080/api/prestamos?rol=GESTOR
	· GET http://localhost:8080/api/prestamos?rol=SISTEMA

	3. Listar solicitudes de un usuario(rol usuario):
 	· GET http://localhost:8080/api/prestamos?rol=USUARIO&usuarioId=?
 	(cambiar valor de usuarioId=? Para obtener información del usuario concreto) 

	4. Listar una solicitud por ID (solo GESTOR): 
	· GET http://localhost:8080/api/prestamos/{UUID}?rol=GESTOR 
	(siendo UUID el identificador de un préstamo existente)

	5. Cambiar estado de una solicitud (solo GESTOR): 
	· PATCH http://localhost:8080/api/prestamos/{UUID}/estado?rol=GESTOR (siendo UUID el identificador de un préstamo existente)
	 
	· Body (JSON)
	   { 	
		"estado": "APROBADA"
	   } el resto de estados son “RECHAZADA” y ”CANCELADA”


---------
## 5.Arquitecturas y decisiones técnicas

### 5.1 Arquitectura en capas

 - Controller: expone la API REST
 - Service: contiene la lógica de negocio
 - Repository: acceso a datos (en memoria con H2)
 - Model: entidades del dominio
 - DTO: objetos de entrada y salida
 - Exception: manejo global de errores

### 5.2 Decisiones Técnicas

 - Spring Boot 4.0.1 para simplificar configuración
 - Spring Data JPA para repositorios
 - DTOs para no exponer entidades directamente
 - Enums para roles y estados
 - Validaciones con Bean Validation
 - UUID para identificar préstamos
 - Control global de errores con @ControllerAdvice
 - H2 en memoria para pruebas rápidas


---------
## 6.Mejoras y extensiones futuras

### 6.1Funcionales

   -Autenticación real (Spring Security + JWT)
   -Gestión completa de usuarios
   -Historial de cambios de estado
   -Filtros por fecha, importe o estado
   -Reglas automáticas de aprobación (Implementar lógica que apruebe o rechace solicitudes automáticamente según condiciones)

### 6.2 Técnicas / arquitecturales

   -Persistencia real en base de datos (PostgreSQL, MySQL)
   -Tests unitarios y de integración
   -Documentación con Swagger / OpenAPI
   -Dockerización del proyecto
   -Versionado de la API
   

########-- MARÍA JESÚS CAÑETE GOMEZ --##########