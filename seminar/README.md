# Seminar - technische Vorrausetzungen

## Tools

- git
- Docker
- kubectl
- gloud

## Verbindung mit Kubernetes Cluster aufnehmen

```shell
# k8s-schulung.json muss in wurzel des Projektes kopiert werden
gcloud auth activate-service-account developer-viadee@k8s-schulung.iam.gserviceaccount.com --key-file=k8s-schulung.json

# Zur Verbindung mit dem Übungscluster, bitte noch folgenden Befehl ausführen:
gcloud container clusters get-credentials cluster-01 --zone europe-west3-a

# Test ob Schritte davor erfolgreich
## Sollte alle verfügbaren Nodes auflisten und insb. keine Fehlermeldung werfen.
kubectl get nodes
```
