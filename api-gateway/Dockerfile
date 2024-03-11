FROM openjdk:11
RUN mkdir /app
WORKDIR /app
COPY target/api-gateway-0.0.1-SNAPSHOT.jar /app
EXPOSE 8082
CMD ["--spring.profiles.active=gcp"]
ENTRYPOINT ["java", "-jar", "api-gateway-0.0.1-SNAPSHOT.jar"]