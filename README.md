# Todo API

![Java](https://img.shields.io/badge/Java-21-informational?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.9-red?logo=apachemaven)

A secure, user-specific RESTful API for managing todos, built with Spring Boot, PostgreSQL, and JWT authentication.

---

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [API Endpoints](#api-endpoints)
- [Setup & Installation](#setup--installation)
- [Authentication](#authentication)
- [Future Improvements](#future-improvements)
- [License](#license)

---

## Overview
This project is a backend Todo management application designed to handle **user-specific todo operations**. Each user can only manage their own todos, and admins have the ability to view all todos. The API is secure, front-end ready, and provides JSON responses for easy integration.

---

## Features
- **User Authentication & Authorization** using JWT
- **User-specific Todo Management**: Create, update, delete, and view your own todos
- **Admin Access**: View all todos
- **PostgreSQL Database Integration** with Spring Data JPA
- **Secure API Endpoints** with Spring Security
- **Validation & Exception Handling** for robust operations
- **Front-end Ready** JSON responses

---

## Technologies Used
- **Java 21**
- **Spring Boot 3.5**
- **Spring Security & JWT**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Lombok**
- **Swagger / OpenAPI** for documentation

---

## API Endpoints

### Auth
| Method | Endpoint           | Description                  |
|--------|------------------|------------------------------|
| POST   | `/auth/register`  | Register a new user          |
| POST   | `/auth/login`     | Login and receive JWT token  |

### Todos
| Method | Endpoint                     | Description                         |
|--------|------------------------------|-------------------------------------|
| GET    | `/api/v1/todo/all`           | Get all todos for logged-in user    |
| GET    | `/api/v1/todo/{id}`          | Get todo by ID (must belong to user)|
| GET    | `/api/v1/todo/paged`         | Get todos by page & sorting         |
| POST   | `/api/v1/todo/create`        | Create a new todo                   |
| PUT    | `/api/v1/todo/update`        | Update a todo                       |
| DELETE | `/api/v1/todo/delete/{id}`   | Delete a todo by ID                 |
| DELETE | `/api/v1/todo/delete/all`    | Delete all todos (admin or user)   |

---

## Setup & Installation

1. **Clone the repository**
```bash
git clone https://github.com/ash-2-code-12/Spring-Boot-Todo-API.git
```
## Configure PostgreSQL

1. **Create a database**
```sql
CREATE DATABASE tododb;
```
2. **Update credentials in `src/main/resources/application.properties`**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tododb
spring.datasource.username=your_username
spring.datasource.password=your_password
```
### Build and run the project
```bash
mvn clean install
mvn spring-boot:run
```
### Access API
-Base URL: http://localhost:8080
-Swagger UI: http://localhost:8080/swagger-ui/index.html


## Authentication

- All Todo endpoints require a **Bearer JWT token** in the `Authorization` header.
- Tokens are obtained via `/auth/login`.

---

## Future Improvements

- Add **user profile management**
- Add **due dates, priorities, and categories** for todos
- Implement **soft delete** for todos
- Add **unit and integration tests**
- Implement **role-based access control** for more fine-grained permissions
