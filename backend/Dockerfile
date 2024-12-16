# Stage 1: Build the application
FROM eclipse-temurin:21-jdk AS builder

# Install Gradle
RUN apt-get update && apt-get install -y wget unzip \
    && wget https://services.gradle.org/distributions/gradle-8.2.1-bin.zip \
    && unzip gradle-8.2.1-bin.zip -d /opt/ \
    && ln -s /opt/gradle-8.2.1/bin/gradle /usr/bin/gradle \
    && gradle --version

WORKDIR /home/gradle/project

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN ./gradlew dependencies

COPY src src

RUN ./gradlew build --no-daemon

# Stage 2: Run the application
FROM openjdk:21-jdk

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "app.jar"]
