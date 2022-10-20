FROM openjdk:11
ARG JAR_FILE=build/libs/manager-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 0
ENTRYPOINT ["java","-jar","/app.jar"]