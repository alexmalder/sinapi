FROM gradle:8.1.1-jdk17 AS cache
WORKDIR /app
#COPY . .
#RUN gradle build --no-daemon --stacktrace
#ENV GRADLE_USER_HOME /cache
COPY build.gradle settings.gradle ./
RUN gradle --no-daemon build --stacktrace
COPY src/ src/
RUN gradle --no-daemon build --stacktrace


#FROM openjdk:17
#WORKDIR /app
#COPY --from=builder /app/build/libs/frontend.jar /frontend.jar
#CMD ["java", "-jar", "-Dspring.profiles.active=default", "/frontend.jar"]
