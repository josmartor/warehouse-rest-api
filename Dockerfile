FROM openjdk:11-jdk AS builder
WORKDIR /usr/build
COPY src src
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
RUN ./mvnw package -DskipTests

FROM openjdk:11-jre
WORKDIR /usr/app
COPY --from=builder /usr/build/target/warehouse-0.0.1-SNAPSHOT.jar warehouse-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","warehouse-0.0.1-SNAPSHOT.jar"]