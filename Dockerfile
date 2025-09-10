# Stage 1: Build the Java application using Maven (This stage is perfect, no changes)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# This RUN command will leverage the cache if pom.xml hasn't changed
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application with a slim JRE
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# The command to run the application. This is the container's main process.
ENTRYPOINT ["java", "-jar", "app.jar"]