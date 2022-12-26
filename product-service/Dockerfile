FROM openjdk:17-jdk-slim
LABEL "Developer"="Tihomir Yordanov"
WORKDIR /app
ADD target/product-service-1.0-SNAPSHOT.jar product-service-1.0-SNAPSHOT.jar
EXPOSE 8083
CMD ["java", "-jar", "product-service-1.0-SNAPSHOT.jar"]
ENTRYPOINT ["java", "-jar", "product-service-1.0-SNAPSHOT.jar"]