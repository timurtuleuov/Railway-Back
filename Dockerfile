FROM alpine:3.18.2

RUN apk add openjdk17
COPY build/libs/space-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]