FROM eclipse-temurin:17-jdk AS build

WORKDIR /build
COPY . /build

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre AS app
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /build/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]

