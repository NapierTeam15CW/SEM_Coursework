FROM openjdk:latest
COPY ./target/group15SEMCoursework.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "group15SEMCoursework.jar", "db:3306"]