FROM openjdk:11
RUN mkdir /app
WORKDIR /app
COPY target/restaurants-and-menu-service-0.0.1-SNAPSHOT.jar /app
EXPOSE 8761
CMD ["--spring.profiles.active=gcp"]
ENTRYPOINT ["java", "-jar", "restaurants-and-menu-service-0.0.1-SNAPSHOT.jar"]