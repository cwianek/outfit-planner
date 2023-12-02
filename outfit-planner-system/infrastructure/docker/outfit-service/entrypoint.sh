#!/bin/bash

CERTS_DIR="/app/certificates"
SERVER_PORT="${SERVER_PORT:-8080}"  # Use 8080 as the default if SERVER_PORT is not set

# Import certificates
for CERT_FILE in $CERTS_DIR/*.pem; do
    KEYSTORE_PASS="changeit"
    ALIAS=$(basename "$CERT_FILE" .pem)

    keytool -import -trustcacerts -keystore $JAVA_HOME/lib/security/cacerts -storepass $KEYSTORE_PASS -noprompt -alias $ALIAS -file $CERT_FILE
done

if [ -n "$WILDFLY_MANAGEMENT_USER" ] && [ -n "$WILDFLY_MANAGEMENT_PASSWORD" ]; then
    /opt/jboss/wildfly/bin/add-user.sh -u $WILDFLY_MANAGEMENT_USER -p $WILDFLY_MANAGEMENT_PASSWORD --silent
fi

# Start WildFly with the specified server port
/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -Djboss.http.port=$SERVER_PORT