FROM openjdk:latest
COPY ./target/semGroup15-0.1.0.6-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "semGroup15-0.1.0.6-jar-with-dependencies.jar"]