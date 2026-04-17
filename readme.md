![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=openjdk&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apachemaven&logoColor=white)
![OpenAPI](https://img.shields.io/badge/OpenAPI-6BA539?style=flat&logo=openapiinitiative&logoColor=white)
![Junie](https://img.shields.io/badge/Junie-6BA539?style=flat&logo=jetbrains&logoColor=white)

# Asset Management System

A Spring Boot-based application for managing corporate assets and employees. It features a RESTful API, a Thymeleaf-based web interface, and PostgreSQL integration with Docker support.
### [Deploy link](https://assetmanagement-b4z4.onrender.com/login)
### [API Documentation](https://assetmanagement-b4z4.onrender.com/v3/api-docs)
### [Swagger UI](https://assetmanagement-b4z4.onrender.com/swagger-ui.html)

## Features

- **Asset Management**: Create, search, update, and remove assets. Filter assets by type, name, and assigned employee.
- **Employee Management**: Manage employee profiles and handle login/authentication.
- **Web Interface**: 
  - Login page for employee authentication.
  - Searchable asset dashboard.
- **Dynamic Queries**: Uses JPA Specifications for flexible filtering and pagination.
- **Data Persistence**: PostgreSQL database integration.
- **Containerization**: Docker and Docker Compose support for easy deployment.
- **Sample Data**: Automatically populates the database with initial departments, employees, and assets on startup.

## Tech Stack

- **Backend**: Java 17, Spring Boot 4.0.5
- **Database**: PostgreSQL 16
- **Template Engine**: Thymeleaf
- **Security**: Spring Security (configured for web access)
- **Documentation**: SpringDoc OpenAPI (Swagger UI)
- **Build Tool**: Maven
- **Infrastructure**: Docker, Docker Compose

## Getting Started

### Prerequisites

- JDK 17 or higher
- Docker and Docker Compose
- Maven (optional, if using the provided `mvnw` wrapper)

### Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd AssetManagement
   ```

   2. **Environment Variables**:
      Create a `.env` file in the root directory based on `.env.example`:
      ```env
      DB_NAME=assets-storage
      DB_PASSWORD=password
      DB_USERNAME=user_name
      DB_PORT=5432#base port of postgres
      ACCESS_KEY=key_for_access_api
      # if DB_URL not empty it will use this url
      DB_URL=jdbc:your_db_url
      ```

3. **Start the Database**:
   ```bash
   docker-compose up -d
   ```

### Running the Application

1. **Build and Run**:
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Access the Web Interface**:
   - Login: [http://localhost:8080/login](http://localhost:8080/login)
   - Assets Dashboard: [http://localhost:8080/assets](http://localhost:8080/assets)

3. **API Documentation**:
   - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Project Structure

- `src/main/java`: Backend source code (Entities, Repositories, Services, Controllers).
- `src/main/resources/templates`: Thymeleaf HTML templates.
- `src/main/resources/data.sql`: Initial data script.
- `src/test/java`: Unit and integration tests.
- `Dockerfile` & `docker-compose.yml`: Containerization configuration.

## Sample Data (included in data.sql)

- **Login for testing**:
  - Email: `john.doe@example.com`
  - Password: `password123`
- **Other Employees**: `jane.smith@example.com`, `alice.johnson@example.com`
- **Assets**: Laptops, Chairs, and Desks assigned to various employees.
