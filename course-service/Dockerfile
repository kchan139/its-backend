# COURSE SVC DOCKERFILE

# --- STAGE 1: BUILD ---
FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src /app/src

RUN mvn package -DskipTests

# --- STAGE 2: RUNTIME ---
FROM eclipse-temurin:21-jre-alpine AS final

RUN addgroup -S springboot && \
    adduser -S -G springboot springboot

COPY --from=build /app/target/*.jar app.jar

RUN chown springboot:springboot app.jar

USER springboot

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
