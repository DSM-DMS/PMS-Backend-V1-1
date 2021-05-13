FROM openjdk:11-jdk
MAINTAINER Kim, Jungbin <smoothbear04@gmail.com>

COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
