# Information Security Policy (ISO 27001)

## 1. Objective
The objective of this Information Security Policy is to ensure the confidentiality, integrity, and availability of the Enterprise Resource Planning (ERP) system and its data.

## 2. Scope
This policy applies to all systems, networks, applications, and personnel that interact with the ERP environment, including staging and production AWS EKS clusters.

## 3. Data Protection
- **Encryption at Rest**: All PostgreSQL and Redis databases are encrypted using AES-256 via AWS KMS.
- **Encryption in Transit**: All communication between internal microservices and external clients is enforced over TLS 1.3.

## 4. Access Control (RBAC & IAM)
- **Principle of Least Privilege**: Access to production environments requires multi-factor authentication (MFA) and is strictly granted on a least-privilege basis.
- **Identity Provider**: Authentication is federated via Okta using SAML 2.0 / OAuth2.

## 5. Incident Management
All security incidents must be reported to the Security Operations Center (SOC) immediately. Automated alerts via PagerDuty will trigger Runbook executing procedures within 5 minutes of a critical alert.
