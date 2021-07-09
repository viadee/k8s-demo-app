FROM maven:3.6-jdk-11-slim as build_container

WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY  . /usr/src/build
WORKDIR /usr/src/build
RUN mvn package -B

FROM openjdk:11-slim
COPY --from=build_container /usr/src/build/target/k8s-demo-app.jar app.jar
RUN groupadd -g 1000 appuser && \
    useradd -u 1000 -g 1000 appuser
USER appuser

EXPOSE 8080/tcp
ENTRYPOINT ["java","-jar","/app.jar"]
