FROM amazoncorretto:11.0.8-alpine

ENV PYTHONUNBUFFERED=1
ENV APP_NAME=disbursements

ARG JAR_FILE=build/libs/${APP_NAME}-1.0-SNAPSHOT.jar

WORKDIR /opt/app-root

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]