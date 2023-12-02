FROM quay.io/wildfly/wildfly:27.0.0.Final-jdk17

WORKDIR /app

#COPY build/libs/* /app/
USER root
# Entrypoint script to import certificates at runtime
COPY entrypoint.sh /app/
RUN chmod +x /app/entrypoint.sh

# Start the application
CMD ["/app/entrypoint.sh"]
# Copy the built WAR file to the deployments directory
