FROM gradle:8.1.1-jdk17 AS builder
WORKDIR /app
COPY build.gradle settings.gradle ./
RUN gradle --no-daemon dependencies
COPY src/ src/
RUN gradle --no-daemon build --stacktrace

FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/build/libs/spring-boot-0.0.1-SNAPSHOT.jar /spring-boot-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "-Dspring.profiles.active=default", "/spring-boot-0.0.1-SNAPSHOT.jar"]
