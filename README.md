# SpringIdeals
This Project clears the basic concepts of Spring Boot
<br>
Status: **WIP**

## Credentials:

### 1. Admin
username: ***admin*** <br>
password: ***admin123***

### 2. User
username: ***user*** <br>
password: ***user123***

## Swagger:
http://localhost:9090/swagger-ui.html <br>
http://localhost:9090/swagger-ui/index.html <br>
http://localhost:9090/v3/api-docs <br>
http://localhost:9090/v3/api-docs.yaml

## SonarQube:
Start Server: `./bin/macosx-universal-64/sonar.sh start` <br>
Run Command: `mvn clean verify -DskipTests sonar:sonar -Dsonar.token=<SONAR_TOKEN>` <br>

Restart Server: `./bin/macosx-universal-64/sonar.sh restart` <br>
Stop Server: `./bin/macosx-universal-64/sonar.sh stop` <br>
Default Server: http://localhost:9000/

## Docker:
Docker Image: `docker build -t spring-ideals:latest . ` <br>
Docker Run: `docker run -p 9090:9090 \ 
-e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/SpringIdeals \
-e SPRING_DATASOURCE_USERNAME=postgres \
-e SPRING_DATASOURCE_PASSWORD=postgres \
-e JWT_SECRET="my_super_secret_key_12345678901234567890" \
--name spring-ideals \
spring-ideals:latest `
