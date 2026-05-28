# 🏗️ C4 Model Architecture Diagrams

This document outlines the Enterprise Architecture of the ERP system using the C4 Model methodology (Context, Container, Component).

## 1. System Context Diagram
Shows how the ERP system fits into the world around it.

```mermaid
graph TD
    User([Enterprise User]) -->|Interacts with| App[ERP System]
    App -->|Sends notifications via| Email[SMTP Email Service]
    App -->|Processes Payments via| Stripe[Stripe API]
    
    classDef system fill:#1168bd,stroke:#0b4884,color:#ffffff;
    classDef external fill:#999999,stroke:#666666,color:#ffffff;
    class App system;
    class Email,Stripe external;
```

## 2. Container Diagram
Zooms into the System boundary to show the high-level technical architecture.

```mermaid
graph TD
    User([User Browser]) -->|HTTPS / REST API| Frontend[Next.js Frontend]
    Frontend -->|HTTPS / REST API| Backend[Spring Boot Backend]
    
    Backend -->|JDBC| Postgres[(PostgreSQL)]
    Backend -->|TCP| Redis[(Redis Cache)]
    Backend -->|Kafka Protocol| Kafka[Apache Kafka]
    
    Prometheus[Prometheus Scraper] -->|HTTP Metrics| Backend
    Grafana[Grafana Dashboard] -->|PromQL| Prometheus
    
    classDef container fill:#438dd5,stroke:#2b5c8f,color:#ffffff;
    classDef db fill:#f2a71d,stroke:#c4820c,color:#ffffff;
    class Frontend,Backend container;
    class Postgres,Redis db;
```

## 3. Component Diagram (Backend Modules)
Zooms into the Spring Boot backend to show the bounded contexts (Modular Monolith).

```mermaid
graph TD
    API[REST Controllers] --> HR[HR Context]
    API --> Sales[Sales Context]
    API --> Inv[Inventory Context]
    API --> Acc[Accounting Context]
    
    Sales -->|Publish OrderEvent| Kafka[Kafka Broker]
    Kafka -->|Consume OrderEvent| Acc
    Kafka -->|Consume OrderEvent| Inv
    
    HR -->|Read/Write| DB[(ERP Database)]
    Sales -->|Read/Write| DB
    Inv -->|Pessimistic Lock| DB
    Acc -->|Read/Write| DB
```
