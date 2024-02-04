FROM selenium/standalone-chrome:latest

COPY . /usr/src/app

WORKDIR /usr/src/app


USER root
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    rm -rf /var/lib/apt/lists/* \
#COPY integration-tests.jar /usr/src/app



# Use a base image with Java and a browser (e.g., Selenium base image)
#FROM selenium/standalone-chrome:latest

# Set the working directory in the container

# Copy your Selenide tests JAR file to the container
#COPY target/your-tests.jar /usr/src/app/

# Specify the command to run on container startup
CMD ["java", "-jar", "integration-tests.jar"]