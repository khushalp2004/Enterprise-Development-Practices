# Security Policy

## Supported Versions

Only the current major version of ERP Nexa receives security updates.

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |
| < 1.0   | :x:                |

## Reporting a Vulnerability

Security is a top priority for ERP Nexa. If you discover a vulnerability, please DO NOT report it by creating a public GitHub issue. 

Instead, please send an email to `security@erpnexa.com` with the subject `[SECURITY VULNERABILITY]`. Include detailed steps to reproduce the issue. We will respond within 24 hours and aim to patch critical vulnerabilities within 48 hours.

## Security Practices
- **Scanning:** All pull requests are automatically scanned using OWASP Dependency-Check.
- **Authentication:** The API is secured via stateless JWT tokens with RSA signatures.
- **Data Protection:** All PII in the database is encrypted at rest using AES-256.
