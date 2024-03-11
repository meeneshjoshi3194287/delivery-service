FROM openjdk:11
RUN mkdir /app
WORKDIR /app
COPY target/payment-service-0.0.1-SNAPSHOT.jar /app
EXPOSE 8085
CMD ["--spring.profiles.active=gcp"]
ENTRYPOINT ["java", "-jar", "payment-service-0.0.1-SNAPSHOT.jar"]