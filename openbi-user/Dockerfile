FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} openbi-user-service.jar

ENTRYPOINT ["java","-jar","/openbi-user-service.jar","--spring.profiles.active=prod"]

EXPOSE 8001