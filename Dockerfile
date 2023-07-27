FROM openjdk:20-jdk
EXPOSE 8080
RUN mkdir /app
COPY ./build/libs/*.jar /app/spring-app.jar
ENTRYPOINT ["java","-jar","/app/spring-app.jar"]