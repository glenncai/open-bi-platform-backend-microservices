FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} openbi-key-service.jar

ENTRYPOINT ["java","-jar","/openbi-key-service.jar","--spring.profiles.active=prod"]

EXPOSE 8004