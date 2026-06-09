# Product Management API

## Overview

This project is a RESTful API built using Spring Boot for managing products. It provides CRUD operations on products and includes JWT-based authentication and role-based authorization to secure the APIs.

The application uses PostgreSQL for data persistence and Swagger/OpenAPI for API documentation and testing.

---

## Features

* User Registration and Login
* JWT Authentication and Authorization
* Product CRUD Operations
* Role-Based Access Control
* Request Validation using Bean Validation
* Global Exception Handling
* Swagger UI for API Documentation and Testing
* Unit and Integration Tests

---

## Technologies Used

* Java 17
* Spring Boot 3
* Spring Security
* Spring Data JPA
* PostgreSQL
* JWT (JJWT)
* Swagger/OpenAPI
* Maven
* JUnit 5 and Mockito

---

## Prerequisites

Before running the application, ensure the following are installed:

* Java 17
* PostgreSQL
* Maven (or use the Maven Wrapper included with the project)

---

## Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE product_service;
```

---

## Application Configuration

The application reads database and JWT configurations from environment variables. Default values are provided for local development.

Current configuration:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/product_service}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:postgres}

app.jwt.secret=${JWT_SECRET:change-this-development-secret-key-with-at-least-256-bits}
app.jwt.expiration-ms=${JWT_EXPIRATION_MS:3600000}
```

### IntelliJ Users

If you are running the application from IntelliJ IDEA, configure the following environment variables in your Spring Boot Run Configuration:

```text
DB_URL=jdbc:postgresql://localhost:5432/product_service
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=9b4d8c3f1e7a6b2c5d8e1f4a7b9c2d5e8f1a4b7c9d2e5f8a1b4c7d9e2f5a8b1
JWT_EXPIRATION_MS=3600000
```

If these environment variables are not provided, the application will use the default values specified in the configuration.

---

## Running the Application

### Using IntelliJ IDEA

* Configure the required environment variables.
* Run the Spring Boot application using the Run button.

### Using Maven Wrapper

Windows:

```bash
.\mvnw.cmd spring-boot:run
```

Linux/Mac:

```bash
./mvnw spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

## Swagger Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

You can use Swagger to explore and test the APIs.

For secured endpoints:

1. Register and login.
2. Copy the JWT token returned by the login API.
3. Click the **Authorize** button in Swagger.
4. Enter the token in the following format:

```text
Bearer <your-jwt-token>
```

---

## Authentication APIs

### Register User

**POST** `/api/auth/register`

Sample Request:

```json
{
    "username": "admin",
    "email": "admin@example.com",
    "password": "password123",
    "role": "ROLE_ADMIN"
}
```

---

### Login

**POST** `/api/auth/login`

Sample Request:

```json
{
    "username": "admin",
    "password": "password123"
}
```

A JWT access token will be returned upon successful authentication.

---

## Product APIs

| Method | Endpoint             | Access              |
| ------ | -------------------- | ------------------- |
| GET    | `/api/products`      | Authenticated Users |
| GET    | `/api/products/{id}` | Authenticated Users |
| POST   | `/api/products`      | Admin Only          |
| PUT    | `/api/products/{id}` | Admin Only          |
| DELETE | `/api/products/{id}` | Admin Only          |

---

## Running Tests

To execute all tests:

Windows:

```bash
.\mvnw.cmd test
```

Linux/Mac:

```bash
./mvnw test
```

---

## Building the Project

To generate the executable JAR file:

Windows:

```bash
.\mvnw.cmd clean package
```

Linux/Mac:

```bash
./mvnw clean package
```

The generated JAR file will be available in the `target` directory.

---

## Notes

* Passwords are stored using BCrypt hashing.
* JWT is used for stateless authentication.
* Swagger has been configured to support Bearer token authentication.
* PostgreSQL is used as the relational database.

---