FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} openbi-config-server.jar

ENTRYPOINT ["java","-jar","/openbi-config-server.jar","--spring.profiles.active=prod"]

EXPOSE 8080