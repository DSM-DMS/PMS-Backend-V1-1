FROM openjdk:11-jdk
MAINTAINER Kim, Jungbin <smoothbear04@gmail.com>

COPY ./spring /spring
WORKDIR /spring

CMD ["./gradlew", "bootRun"]