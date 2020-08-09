FROM adoptopenjdk/openjdk11
COPY /target/SessionHandlerService-0.0.1-SNAPSHOT.jar SessionHandlerService-0.0.1-SNAPSHOT.jar
EXPOSE 9000
ENTRYPOINT ["java","-jar","SessionHandlerService-0.0.1-SNAPSHOT.jar"]