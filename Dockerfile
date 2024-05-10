# Stage 1: Install Maven and download dependencies
FROM maven:3.8.4-openjdk-17 AS dependencies

# Copy only the pom.xml file to the Docker image
COPY pom.xml /app/

# Resolve Maven dependencies (this layer will be cached)
RUN mvn -f /app/pom.xml dependency:resolve

# Stage 2: Build the Maven project
FROM dependencies AS build

# Copy the entire project to the Docker image
COPY . /app/

# Build the Maven project (this layer will be cached)
RUN mvn -f /app/pom.xml clean install -DskipTests

# Stage 3: Create the final image
FROM eclipse-temurin:17-jdk-alpine

# Copy the JAR file from the build stage to the final image
COPY --from=build /app/target/*.jar /app.jar

# Define the entry point
ENTRYPOINT ["java","-jar","/app.jar"]

# Expose the container port
EXPOSE 8080
