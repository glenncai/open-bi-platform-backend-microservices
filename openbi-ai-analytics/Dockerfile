FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} openbi-analytics-service.jar

ENTRYPOINT ["java","-jar","/openbi-analytics-service.jar","--spring.profiles.active=prod"]

EXPOSE 8003