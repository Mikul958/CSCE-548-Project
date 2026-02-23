# ------------------------
# Stage 1: Build the App
# ------------------------
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy source and build
COPY . .

# Build JAR
RUN ./mvnw clean package -DskipTests

# ------------------------
# Stage 2: Run the App
# ------------------------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar", "csce548-0.0.1-SNAPSHOT.jar"]