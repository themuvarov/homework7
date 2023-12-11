FROM openjdk:17-jdk-alpine
MAINTAINER m.uvarov@tinkoff.ru
ADD ./target/notify-service-0.0.4-SNAPSHOT.jar ./app.jar
EXPOSE 7878
ENTRYPOINT ["java", "-jar","/app.jar"]
