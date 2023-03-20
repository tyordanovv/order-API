FROM openjdk:17-jdk-slim
LABEL "Developer"="Tihomir Yordanov"
WORKDIR /app
ADD target/auth-service-1.0-SNAPSHOT.jar auth-service-1.0-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "auth-service-1.0-SNAPSHOT.jar"]
ENTRYPOINT ["java", "-jar", "auth-service-1.0-SNAPSHOT.jar"]