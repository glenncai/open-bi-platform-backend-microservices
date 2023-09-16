FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} openbi-config-server.jar

ENTRYPOINT ["java","-jar","/openbi-config-server.jar"]

EXPOSE 8080