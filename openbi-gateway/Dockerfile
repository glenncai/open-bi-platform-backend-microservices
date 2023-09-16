FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} openbi-gateway.jar

ENTRYPOINT ["java","-jar","/openbi-gateway.jar"]

EXPOSE 8000