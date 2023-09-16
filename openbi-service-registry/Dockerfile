FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} openbi-service-registry.jar

ENTRYPOINT ["java","-jar","/openbi-service-registry.jar"]

EXPOSE 8761