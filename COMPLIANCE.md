# Compliance Guidelines

ERP Nexa is built with enterprise compliance standards in mind. This document outlines how the system adheres to major regulatory frameworks.

## ISO 27001
- **Access Control:** All endpoints are strictly regulated by Role-Based Access Control (RBAC). 
- **Audit Logging:** We maintain an immutable audit trail of critical actions (e.g., `CREATE_EMPLOYEE`, `UPDATE_INVENTORY`).
- **Cryptography:** TLS 1.3 is enforced for all data in transit.

## GDPR (General Data Protection Regulation)
- **Data Minimization:** The HR module only collects data strictly necessary for employment processing.
- **Right to Erasure:** Hard deletes are supported for Employee records via the `/api/hr/employees/{id}` DELETE endpoint.
- **Data Portability:** Reporting endpoints allow data export.

## SOC 2
- **Availability:** Multi-region deployment configuration via Kubernetes and AWS EKS ensures high availability.
- **Monitoring:** Prometheus and Grafana are configured for continuous security and availability monitoring.
- **Incident Response:** Automated alerts are sent via PagerDuty when SLOs degrade below 99.95%.
