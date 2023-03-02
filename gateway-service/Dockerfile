FROM openjdk:17-jdk-slim
LABEL "Developer"="Tihomir Yordanov"
WORKDIR /app
COPY target/gateway-service-1.0-SNAPSHOT.jar gateway-service-1.0-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "gateway-service-1.0-SNAPSHOT.jar"]