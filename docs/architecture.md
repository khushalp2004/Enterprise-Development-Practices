# ERP Nexa - Architecture Guidelines

## Overview
ERP Nexa follows a **Modular Monolith** architecture built with Spring Boot. This provides the best of both worlds: single deployment convenience for local/staging environments, but logical segregation of domains (HR, Inventory, Sales, Accounting) so they can be extracted into true microservices later.

## Domain Segregation
- **`erp-core`**: Contains cross-cutting concerns (Security configurations, Exception Handlers, JWT filters, Kafka templates, Audit logging).
- **`erp-hr`**, **`erp-inventory`**, **`erp-sales`**, **`erp-accounting`**: Distinct business domains.
- **`erp-application`**: The root module that packages all sub-modules into an executable `.jar`.

## Event-Driven Communication
We use **Apache Kafka** to decouple domains. 
For example: When the `erp-sales` module successfully completes a checkout, it publishes a `SaleCompletedEvent`. The `erp-inventory` module listens to this event to autonomously deduct stock, and `erp-accounting` listens to it to autonomously update the general ledger.

## Frontend Architecture
The web application (`/erp-frontend`) is a Next.js (React) SSR framework.
- Uses `react-hot-toast` for unified notifications.
- Proxies `/api/*` traffic via `next.config.ts` to the backend on port `8080` to prevent CORS issues.
- Client-side token storage in `localStorage`.
