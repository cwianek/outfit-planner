FROM quay.io/wildfly/wildfly:27.0.0.Final-jdk17

COPY outfit-planner-system/outfit-service/target/outfit-service-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
COPY entrypoint.sh /app/

WORKDIR /app

# Entrypoint script to import certificates at runtime

USER root
RUN chmod +x /app/entrypoint.sh

RUN ls /app

COPY outfit-planner-system/outfit-service/target/outfit-service-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
# Start the application
CMD ["/app/entrypoint.sh"]
# Copy the built WAR file to the deployments directory
