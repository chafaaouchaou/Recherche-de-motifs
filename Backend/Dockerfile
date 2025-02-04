# Use Maven with JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy only the pom.xml first (to cache dependencies)
COPY pom.xml . 

# Download dependencies
RUN mvn dependency:go-offline

# Copy the full source code
COPY src ./src

# Expose port for hot reload
EXPOSE 9091

# Enable live reload
ENTRYPOINT ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Dspring.devtools.restart.enabled=true"]
