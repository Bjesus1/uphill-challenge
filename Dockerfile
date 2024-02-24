FROM openjdk:17

COPY target/*.jar /usr/src/uphill-challenge-1.0-SNAPSHOT.jar
COPY target/classes/application.yml /usr/src/application.yml

WORKDIR /usr/src

CMD java \
-jar uphill-challenge-1.0-SNAPSHOT.jar

EXPOSE 15550