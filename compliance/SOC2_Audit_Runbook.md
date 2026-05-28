# SOC 2 Audit Runbook & Disaster Recovery

## 1. Objective
This runbook details the procedures for maintaining Service Organization Control (SOC) 2 Type II compliance, focusing on the Security, Availability, and Confidentiality trust service criteria.

## 2. Disaster Recovery Strategy (Availability)
- **RTO (Recovery Time Objective)**: 4 Hours
- **RPO (Recovery Point Objective)**: 15 Minutes
- **Procedure**: In the event of a US-East-1 regional outage, the automated Jenkins Blue-Green pipeline will failover to US-West-2. RDS automated backups will be restored in the new region.

## 3. Incident Response (PagerDuty Integration)
When Jaeger detects latency exceeding the 150ms SLO, or Prometheus detects an error rate > 0.05%:
1. PagerDuty automatically triggers a High Severity incident.
2. The On-Call Engineer is paged.
3. The engineer consults the Kibana logs to trace the specific `traceId` associated with the failure.

## 4. Continuous Auditing
- **SAST/DAST**: All pull requests must pass automated Checkmarx and OWASP ZAP scans via GitHub Actions before merging.
- **Log Immutability**: ELK logs are shipped to a Write-Once-Read-Many (WORM) S3 bucket to prevent tampering.
