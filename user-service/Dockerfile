FROM openjdk:11
RUN mkdir /app
WORKDIR /app
COPY target/user-service-0.0.1-SNAPSHOT.jar /app
EXPOSE 8083
CMD ["--spring.profiles.active=gcp"]
ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]
