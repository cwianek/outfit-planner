FROM openjdk:21-jdk-slim

COPY . /app
WORKDIR /app

COPY ./target/integration-tests-1.0-SNAPSHOT.jar ./integration-tests.jar

USER root
RUN chmod +x /app/entrypoint.sh

CMD ["/app/entrypoint.sh"]
