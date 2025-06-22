# ---------- STAGE 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory in the image
WORKDIR /app

# Copy pom.xml and download dependencies first for caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire project
COPY .. .

# Build the application (skipping tests if not needed)
RUN mvn clean package -DskipTests

# ---------- STAGE 2: Run ----------
FROM eclipse-temurin:21-jdk-alpine

# Set working directory in the image
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/projectManagementSystem-0.0.1-SNAPSHOT.jar .

# Expose the application port (default is 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/projectManagementSystem-0.0.1-SNAPSHOT.jar"]
