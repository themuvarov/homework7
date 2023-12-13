FROM openjdk:17-jdk-alpine
MAINTAINER m.uvarov@tinkoff.ru
ADD ./target/bike-service-0.1.6-SNAPSHOT.jar ./app.jar
EXPOSE 7878
ENTRYPOINT ["java", "-jar","/app.jar"]
