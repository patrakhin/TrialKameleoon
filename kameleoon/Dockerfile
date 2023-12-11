FROM openjdk:11-jre-slim
WORKDIR /app
COPY ./target/kameleoon-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
CMD ["java", "-jar", "app.jar"]
