
FROM openjdk:25-jdk


WORKDIR /app


COPY target/merch-shop-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "/app/app.jar"]