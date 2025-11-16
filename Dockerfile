# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only what's needed for caching dependencies
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN mvn -B -f pom.xml dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -B -f pom.xml clean package -DskipTests

# ---------- Run stage ----------
FROM eclipse-temurin:21-jre-jammy
ARG JAR_FILE=/app/target/*.jar
WORKDIR /app

# Copy the jar built in the previous stage
COPY --from=build /app/target/*.jar app.jar

# (Optional) create a non-root user
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring

# Expose the port your app listens on (your app uses 9090)
EXPOSE 9090

# Healthcheck (optional) — checks the root endpoint
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:9090/actuator/health || exit 1

ENTRYPOINT ["java","-jar","/app/app.jar"]