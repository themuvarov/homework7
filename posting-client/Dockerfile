FROM openjdk:17-jdk-alpine
MAINTAINER m.uvarov@tinkoff.ru
ADD ./target/posting-client-0.0.8-SNAPSHOT.jar ./app.jar
EXPOSE 9999
ENTRYPOINT ["java", "-jar","/app.jar"]