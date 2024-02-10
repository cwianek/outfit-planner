FROM openjdk:17.0.2-jdk-slim

COPY . /app

WORKDIR /app

COPY ./target/integration-tests-1.0-SNAPSHOT.jar ./integration-tests.jar

USER root

RUN chmod +x /app/entrypoint.sh

#RUN #apt-get update && apt-get install -y openjdk-17-jdk && rm -rf /var/lib/apt/lists/*
#COPY integration-tests.jar /usr/src/app



# Use a base image with Java and a browser (e.g., Selenium base image)
#FROM selenium/standalone-chrome:latest

# Set the working directory in the container

# Copy your Selenide tests JAR file to the container
#COPY target/your-tests.jar /usr/src/app/

# Specify the command to run on container startup
CMD ["/app/entrypoint.sh"]