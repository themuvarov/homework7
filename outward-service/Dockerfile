FROM openjdk:17-jdk-alpine
MAINTAINER m.uvarov@tinkoff.ru
ADD ./target/tariff-service-0.0.6-SNAPSHOT.jar ./app.jar
EXPOSE 8989
ENTRYPOINT ["java", "-jar","/app.jar"]
