FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} openbi-ip-service.jar

ENTRYPOINT ["java","-jar","/openbi-ip-service.jar","--spring.profiles.active=prod"]

EXPOSE 8002