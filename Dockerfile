# Building Spring Boot application
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY src ./src
RUN chmod +x gradlew && ./gradlew clean build -x test

# Running Spring Boot application
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
