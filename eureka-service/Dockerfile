FROM openjdk:17-jdk-slim
LABEL "Developer"="Tihomir Yordanov"
WORKDIR /app
COPY target/eureka-service-1.0-SNAPSHOT.jar eureka-service-1.0-SNAPSHOT.jar
EXPOSE 8761
CMD ["java", "-jar", "eureka-service-1.0-SNAPSHOT.jar"]