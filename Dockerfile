FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY build/libs/book-shelf-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]