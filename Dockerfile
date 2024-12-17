FROM openjdk:17-jdk-slim

EXPOSE 8083

ARG JAR_FILE=target/feedback-service.jar
ADD ${JAR_FILE} feedback-service.jar

ENTRYPOINT exec java -jar /feedback-service.jar