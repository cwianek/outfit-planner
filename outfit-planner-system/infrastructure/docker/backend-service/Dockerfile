FROM quay.io/wildfly/wildfly:27.0.0.Final-jdk17

COPY . /app/
WORKDIR /app

#only for github action
RUN if [ -f artifact*.war ]; then \
        echo "Artifact found. Copying var file."; \
        cp artifact*.war /opt/jboss/wildfly/standalone/deployments/ ; \
    else \
        echo "Folder 'outfit-planner-system' not found. Skipping file copy."; \
    fi


#COPY build/libs/* /app/
USER root
# Entrypoint script to import certificates at runtime
#COPY entrypoint.sh /app/
RUN chmod +x /app/entrypoint.sh

# Start the application
CMD ["/app/entrypoint.sh"]
# Copy the built WAR file to the deployments directory