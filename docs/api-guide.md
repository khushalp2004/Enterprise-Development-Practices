# API Integration Guide

## Swagger & OpenAPI
We have integrated **Swagger UI** for beautiful, interactive API documentation. 
When the backend is running locally, you can access the full API explorer at:
`http://localhost:8080/swagger-ui/index.html`

The raw OpenAPI JSON schema is available at:
`http://localhost:8080/v3/api-docs`

## Authentication
All API endpoints (except `/api/auth/login` and `/actuator/**`) require a valid JWT token in the `Authorization` header.

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## Core Endpoints
### HR Module
- `GET /api/hr/employees`: List all employees
- `POST /api/hr/employees`: Create an employee
- `PUT /api/hr/employees/{id}`: Update an employee
- `DELETE /api/hr/employees/{id}`: Delete an employee

### Inventory Module
- `GET /api/inventory/products`: List all products
- `POST /api/inventory/products`: Create a product
- `PUT /api/inventory/products/{id}`: Update a product
- `DELETE /api/inventory/products/{id}`: Delete a product

*(Refer to Swagger UI for the complete, up-to-date schema)*
