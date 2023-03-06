FROM adoptopenjdk/maven-openjdk11:latest AS build

COPY src /app/src

WORKDIR /app

COPY pom.xml /app

RUN mvn clean install -DskipTests

RUN ls -lh /app/target

#
# Package stage
#

#FROM openjdk:11-jre-slim

FROM openjdk:11-jre-slim-buster

WORKDIR /app

# COPY config/configs-dev.properties config/

COPY --from=build /app/target/mintyn-consumer-0.0.1.jar mintyn-consumer-0.0.1.jar

EXPOSE 1837

ENTRYPOINT ["java","-jar","mintyn-consumer-0.0.1.jar"]