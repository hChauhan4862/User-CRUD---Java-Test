# Keen & Able HC Application (keenable_hc)

## Overview

The **User CRUD - SpringBoot** is a Spring Boot-based project that handles user registration, bulk user uploads, and authentication. It is built using Spring Boot, Spring Data JPA, MySQL, and other related technologies. The application is capable of processing user data, including CSV-based bulk user registration, and includes a REST API with a variety of endpoints for managing users.

## Features

- **User Registration**: Register new users via single or bulk upload.
- **CSV File Upload**: Upload a CSV file for bulk user registration.
- **User Management**: Retrieve, update, and delete users.
- **Authentication**: Validate users by password.
- **File Upload Configuration**: Handles file uploads and file size limits.
- **MySQL Integration**: Uses MySQL as the database.
- **Spring Data JPA**: Simplifies database operations.

## Technology Stack

- **Spring Boot** - Backend framework
- **Spring Data JPA** - ORM framework
- **MySQL** - Relational Database
- **Maven** - Build tool
- **Hibernate** - JPA implementation
- **Lombok** - For cleaner code (automatic getter/setter generation)
- **Apache Commons CSV** - CSV file parsing
- Java Version - 21
- Spring Boot Version - 3.3

## Setup Instructions

### Prerequisites:
1. **Java 17 or later**: Ensure that you have JDK 17 or later installed.
2. **Maven**: Required for building the project.
3. **MySQL**: Ensure that MySQL is installed and running on your machine.
4. **Postman** or any API client: To test the REST APIs.

### Installation:

1. Clone the repository:
   ```bash
   git clone https://github.com/hChauhan4862/UserCRUD-SpringBoot.git
   cd keenable_hc```
   
2.	Setup MySQL database:
- Create a MySQL database named keenable_hc.
- Update the credentials in the application.properties file:
  ```
  spring.datasource.url=jdbc:mysql://localhost:3306/keenable_hc?useSSL=false&serverTimezone=UTC
  spring.datasource.username=<your-username>
  spring.datasource.password=<your-password>
  ```

3.	Build and run the project using Maven:
  ```
  mvn clean install
  mvn spring-boot:run
  ```
4. The application will start running on http://localhost:8080.
5. Test the endpoints using the [API Documentation](https://www.apidog.com/apidoc/shared-acb43eba-92e3-40c3-b01d-b59063955c0b) 
