FROM gradle:7.2.0-jdk17

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .

RUN gradle build
