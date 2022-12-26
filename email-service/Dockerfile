FROM openjdk:17-jdk-slim
LABEL "Developer"="Tihomir Yordanov"
WORKDIR /app
COPY target/email-service-1.0-SNAPSHOT.jar email-service-1.0-SNAPSHOT.jar
EXPOSE 8081
CMD ["java", "-jar", "email-service-1.0-SNAPSHOT.jar"]