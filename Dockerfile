# Use a Maven image for building the application
FROM maven:3.8.6-openjdk-17-focal as builder

WORKDIR /app
COPY . .

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use a smaller image for running the app (JRE image)
FROM eclipse-temurin:17-jre-focal

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
