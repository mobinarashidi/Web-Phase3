# Use a multi-stage build for optimization
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY ../pom.xml .
COPY ../src ./src
RUN mvn clean package -DskipTests

# Use a lighter image for running the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 5005
ENTRYPOINT ["java", "-jar", "app.jar"]
