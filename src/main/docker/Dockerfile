FROM maven:latest AS build
COPY . /logoped-service
WORKDIR /logoped-service
RUN mvn clean package -DskipTests

FROM openjdk:latest
COPY --from=build /logoped-service/target/logoped-service-0.0.1-SNAPSHOT.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","application.jar"]