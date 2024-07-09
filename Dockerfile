# Use an official Eclipse Temurin OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper zip file
COPY bghelper/gradle/wrapper/gradle-wrapper.jar /app/gradle/wrapper/gradle-wrapper.jar
COPY bghelper/gradle/wrapper/gradle-wrapper.properties /app/gradle/wrapper/gradle-wrapper.properties

# Copy the build.gradle and settings.gradle files
COPY bghelper/build.gradle bghelper/settings.gradle /app/

# Copy the gradle wrapper and the gradle directory
COPY bghelper/gradlew /app/
COPY bghelper/gradle /app/gradle

# Copy the source code
COPY bghelper/src /app/src

# Ensure the gradlew script has execute permissions
RUN chmod +x gradlew

# Copy the built application to the container
COPY bghelper/build/libs/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
