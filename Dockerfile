FROM gradle:7.2.0-jdk17 AS cache
WORKDIR /app
ENV GRADLE_USER_HOME /cache
COPY build.gradle gradle.properties settings.gradle ./
RUN gradle --no-daemon build --stacktrace
COPY src/ src/
RUN gradle --no-daemon build --stacktrace


FROM openjdk:17
WORKDIR /app
RUN apk --no-cache add curl
COPY --from=builder /app/build/libs/frontend.jar /frontend.jar
CMD ["java", "-jar", "-Dspring.profiles.active=default", "/frontend.jar"]
