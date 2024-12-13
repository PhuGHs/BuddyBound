FROM eclipse-temurin:17-jdk-focal as builder

WORKDIR /app
COPY . .
RUN chmod +x gradlew  # Ensure gradlew is executable
RUN ./gradlew build -x test

FROM eclipse-temurin:17-jre-focal

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
