FROM openjdk:17-jdk-slim
LABEL "Developer"="Tihomir Yordanov"
WORKDIR /app
ADD target/order-service-1.0-SNAPSHOT.jar order-service-1.0-SNAPSHOT.jar
EXPOSE 8082
CMD ["java", "-jar", "order-service-1.0-SNAPSHOT.jar"]
ENTRYPOINT ["java", "-jar", "order-service-1.0-SNAPSHOT.jar"]