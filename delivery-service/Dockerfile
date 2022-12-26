FROM openjdk:17-jdk-slim
LABEL "Developer"="Tihomir Yordanov"
WORKDIR /app
COPY target/delivery-service-1.0-SNAPSHOT.jar delivery-service-1.0-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "delivery-service-1.0-SNAPSHOT.jar"]