# Simple demo app for k8s

The goal is to have a simple app that can be used to demonstrate some features you can use in k8s.

This demo app uses spring boot and a h2-database as technologies.

## Features

 - endpoint for a health check, which can be controlled via environment variable
 - endpoint for a readiness check, which can be controlled via rest endpoint
 - background-color and some text customizable via spring config

 ## Running

 Run with docker
 ```shell
 docker run -p 8080:8080 public.ecr.aws/viadee/k8s-demo-app:latest
 ```

 Then browse to `http://localhost:8080`

Or build and run with maven locally:

 ```shell
 mvn spring-boot:run
 ```

 ## Building

 This is a simple maven project. Just run `maven package`.

 Or just do a local docker build: `docker build -t imagename .`
 Then you can run the container locally.


## Native Build in github Codespaces

 ```shell
mvn clean
mvn native:compile -Pnative
# If "[1/8] Initializing..." fails, it might be due to a lag of resources. Try increasing the VM resources.
 ```

### start native build
```shell
./target/k8s-demo-app
./target/k8s-demo-app --spring.profiles.active=red
```

## Connecting PostgreSQL DB

Overwrite application properties, e.g. via environment variables:
```
SPRING_DATASOURCE_URL             = "jdbc:postgresql://<url_to_postgres_server_instance>:5432/<database_name>"
SPRING_DATASOURCE_DRIVERCLASSNAME = "org.postgresql.Driver"
SPRING_DATASOURCE_USERNAME        = <postgres_user>
SPRING_DATASOURCE_PASSWORD        = <postgres_password>
```
