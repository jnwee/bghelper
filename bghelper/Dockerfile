# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the build.gradle and settings.gradle files
COPY build.gradle settings.gradle /app/

# Copy the gradle wrapper and the gradle directory
COPY gradlew /app/
COPY gradle /app/gradle

# Copy the source code
COPY src /app/src

# Install the dependencies and build the application
RUN ./gradlew build

# Copy the built application to the container
COPY build/libs/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
