FROM openjdk:8-jdk-alpine
EXPOSE 8081
ADD target/demo-0.0.1-SNAPSHOT.jar prodapp.jar
ENTRYPOINT ["java","-jar","/prodapp.jar"]