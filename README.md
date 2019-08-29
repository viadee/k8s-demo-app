# Simple demo app for k8s
The goal is to have a simple app which can be used to demonstrate some features you can use in k8s.

This demo app uses spring boot and a h2-database as technologies.

## Features
 - endpoint for heath check, which can be controled via environment variable
 - endpoint for readiness check, which can be controled via rest endpoint
 - background-color and some text customizable via spring config

 ## Running
 Run with docker
 ```shell
 docker run viadee/k8s-demo-app:latest
 ```

 ## Building
 This is a simple maven project. Just run ```maven package```.

 Or just do a local docker build: ```docker build -t imagename .```
 Then you can run the container locally.
