FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x gradlew

RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew build -x test --no-daemon

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/task_codepred-0.0.1-SNAPSHOT.jar"]
