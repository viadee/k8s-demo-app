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
 docker run -p 8080:8080 public.ecr.aws/m4k8r3n4/k8s-demo-app:latest
 ```

 Then browse to `http://localhost:8080`

 ## Building

 This is a simple maven project. Just run `maven package`.

 Or just do a local docker build: `docker build -t imagename .`
 Then you can run the container locally.
