FROM eclipse-temurin:25-jre-alpine

RUN apk add --no-cache shadow && \
    groupadd -g 1000 appuser && \
    useradd -u 1000 -g 1000 -m appuser && \
    mkdir -p /app && \
    chown -R appuser:appuser /app && \
    apk del shadow

WORKDIR /app
COPY --chown=appuser:appuser target/k8s-demo-app.jar app.jar

USER appuser
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "app.jar"]
