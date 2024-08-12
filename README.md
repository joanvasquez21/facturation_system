## Diseño UML 
![image](https://github.com/user-attachments/assets/4f227737-b0dd-4885-8356-80a47e7cf2ca)

# Proyecto: Facturación en Spring Boot con JPA

Este proyecto sera una aplicación web  para la facturacion de clientes, desarrollada utilizando Spring Boot, Spring Data JPA, Thymeleaf, y MySQL. La aplicación permitira crear facturas, listar, editar y eliminar clientes en una base de datos MySQL.

## Tabla de Contenidos

1. [Requisitos Previos](#requisitos-previos)
2. [Explicación de las Clases](#explicación-de-las-clases)
3. [Mensajes de Validación](#mensajes-de-validación)
4. [Plantillas Thymeleaf para las vistas](#plantillas-thymeleaf-para-las-vistas)
5. [Ejecución de la Aplicación](#ejecución-de-la-aplicación)


## Requisitos Previos

Antes de comenzar, asegúrate de tener los siguientes requisitos instalados en tu sistema:

1. **Java JDK 17 o superior**
2. **Maven** (para gestionar las dependencias)
3. **MySQL** (para la base de datos)
4. **Git** (para clonar el repositorio)



5. **Clona el Repositorio**:
   ```
   git clone https://github.com/tu-usuario/facturation_system.git
   cd facturation_system
## Explicación de las Clases

### Controlador 
* **ClientController.java**
Este controlador maneja las peticiones HTTP para las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) relacionadas con los clientes.

### Servicios
* **ClientServiceImpl.java**
  Implementa la interfaz IClientService.
  Contiene la lógica de negocio para gestionar clientes, incluyendo las operaciones CRUD.
  Utiliza el repositorio IClientDao para interactuar con la base de datos.

### Entidades
* **Client.java**
  Es una entidad JPA que mapea a la tabla `clients` en la base de datos.
  Contiene campos como id, name, lastName, email, y createAt.
  Incluye validaciones para los campos usando anotaciones de Bean Validation como @NotEmpty, @Email, y @NotNull.

### Repositorio
* **IClientDao.java**
  Extiende CrudRepository, proporcionando métodos CRUD para interactuar con la base de datos.
  Especifica Client como la entidad y Long como el tipo de ID.
  Esto permite que IClientDao maneje operaciones como guardar, buscar, actualizar y eliminar registros de la entidad Client.
## Mensajes de validacion Mensajes de Validación
Los mensajes de validación se definen en messages.properties:

```
NotEmpty.client.name=Name is required
NotEmpty.client.lastName=LastName is required
NotEmpty.client.email=Email is required
Email.client.email=Invalid email format
NotNull.client.createAt=Date cannot be null
typeMismatch.client.createAt=Date format is invalid
```
### Plantillas Thymeleaf para las vistas

* **listClients.html:** Muestra una tabla con la lista de clientes, incluyendo opciones para editar y eliminar.
* **form.html:** Muestra un formulario para crear o editar un cliente.

### Ejecución de la Aplicación
Para ejecutar la aplicación, usa el siguiente comando:
```
mvn spring-boot:run
```
Accede a la aplicación en tu navegador en http://localhost:8080.
