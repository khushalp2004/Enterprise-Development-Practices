# ERP Nexa - Enterprise Resource Planning System

ERP Nexa is a robust, event-driven, modular monolithic Enterprise Resource Planning system built on Spring Boot and Next.js. It features comprehensive business modules designed to handle HR, Inventory, Sales, Accounting, and Reporting at scale.

## Architecture Highlights
- **Backend:** Spring Boot (Java 17), Modular Monolith with Maven multi-module structure.
- **Frontend:** Next.js (React), server-side rendering, and modern UI (Outfit font, Glassmorphism).
- **Messaging:** Apache Kafka for asynchronous, decoupled inter-module communication.
- **Data Persistence:** PostgreSQL for relational data, Redis for high-speed caching.
- **Security:** Stateless JWT Authentication with Role-Based Access Control (RBAC).

## Modules
1. **HR Management:** Employee onboarding and directory.
2. **Inventory Control:** Stock adjustments, dynamic pricing, caching via Redis.
3. **Sales (POS):** Checkout processing fetching dynamic inventory prices.
4. **Accounting:** Background Kafka listener processing general ledger entries and revenue.
5. **Reporting:** Aggregated metrics for a high-level dashboard view.

## Quick Start
```bash
# Start infrastructure (Postgres, Redis, Kafka, Zookeeper, Prometheus, Grafana)
docker-compose up -d

# Start backend
mvn clean install
mvn spring-boot:run -pl erp-application

# Start frontend
cd erp-frontend
npm install
npm run dev
```

For detailed deployment instructions, refer to the `/docs` directory.
