# ⚙️ Operations Manual

This manual provides Day-2 operational procedures for managing the ERP application in production.

## 1. Monitoring Dashboards (Grafana)
Grafana is the primary interface for tracking system health.
- **Access**: `http://localhost:3001` (or production URL)
- **Primary Dashboard**: "JVM (Micrometer)" - Displays heap usage, GC pauses, request throughput, and API latency.
- **Alerting**: If Memory Utilization exceeds 85%, Grafana will fire an alert to PagerDuty.

## 2. Distributed Tracing (Jaeger)
When an alert is triggered (e.g., API latency > 500ms), engineers must use Jaeger to trace the request.
- **Access**: `http://localhost:16686` (via K8s Port Forwarding)
- **Usage**: Search for the `traceId` found in the ELK logs. Jaeger will visualize the exact bottleneck across the bounded contexts (e.g., showing if the delay occurred during the Postgres Lock in the Inventory module).

## 3. Managing Kafka Topics
If a dead-letter queue (DLQ) fills up, or offsets need to be reset:
- Connect to the Kafka container/pod:
  ```bash
  kubectl exec -it erp-kafka-0 -- /bin/bash
  ```
- List Topics:
  ```bash
  kafka-topics --bootstrap-server localhost:9092 --list
  ```

## 4. Scaling the Application
To handle increased enterprise load, the stateless backend can be scaled horizontally.
```bash
kubectl scale deployment erp-backend --replicas=5
```
*(Note: Do not scale StatefulSets like PostgreSQL manually without consulting the DBA team regarding replication setups).*
