# 📚 API Documentation (OpenAPI / Swagger)

The ERP System is built with a RESTful API adhering to the OpenAPI 3.0 specification. 

Because APIs evolve rapidly during development, we do not maintain static API documentation. Instead, the backend dynamically generates interactive documentation via **Swagger UI** using the `springdoc-openapi` library.

## How to Access the API Documentation
When the backend application is running locally, you can view the fully interactive API documentation, schema definitions, and execute requests directly from your browser.

1. Ensure the backend is running (`mvn spring-boot:run -pl erp-application`).
2. Open your browser and navigate to:
   - **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - **OpenAPI JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Authentication (JWT)
Most API endpoints are secured. To test secured endpoints via the Swagger UI:
1. Hit the `POST /api/auth/login` endpoint with valid credentials to receive a JWT Token.
2. Click the green **Authorize** button at the top of the Swagger UI.
3. Paste your token in the format: `Bearer <your-jwt-token>`.
4. All subsequent API calls made from Swagger will include the Authorization header.
