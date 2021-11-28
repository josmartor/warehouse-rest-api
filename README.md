# Tracking Device Configs REST API example
This is a simple example how you can use spring boot + mongodb + docker to create a REST API.

- [x] Spring Boot
- [x] Spring Data
- [x] MongoDB
- [x] Docker
- [x] Lombok
- [x] Unit Tests
- [x] Integration Tests

**System requirements**
- JDK 11
- Maven
- Docker

## Build and run application
The file [docker-compose.yml](docker-compose.yml) contains all the necessary settings to configure the environment. 

As this is a very simple application, we will basically have only two containers/services - the rest api and the database. If you want to  build or rebuild services to include any code changes in the docker image(s), run `docker-compose build`. To start all the services, you need to run `docker-compose up` (use `-d` to run in detached mode). The docker image for the rest api application service is defined by the file [Dockerfile](Dockerfile) using multi stages in order to build and execute the application.

The rest api application will be available at http://localhost:8081.

Enjoy!
