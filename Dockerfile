FROM eclipse-temurin:17-jdk-focal as builder

WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-focal

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
COPY credentials.json /usr/src/app/credentials.json
ENV GOOGLE_APPLICATION_CREDENTIALS=/usr/src/app/credentials.json

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]