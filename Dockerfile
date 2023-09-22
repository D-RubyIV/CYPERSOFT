FROM MAVEN:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/cyperoft-0.0.1-SNAPSHPT.jar cyperoft.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "cybersoft.jar" ]