FROM eclipse-temurin:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY APIKEY.json SolarCarTelemetry/src/main/resources/application.properties.json
ENTRYPOINT ["java","-jar","/app.jar"]