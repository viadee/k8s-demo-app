FROM openjdk:11-slim
COPY target/k8s-demo-app.jar app.jar
RUN groupadd -g 1000 appuser && \
    useradd -u 1000 -g 1000 appuser
USER appuser

EXPOSE 8080/tcp
ENTRYPOINT ["java","-jar","/app.jar"]
