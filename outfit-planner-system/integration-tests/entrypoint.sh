#!/bin/bash

APP_URL="${APP_URL:-default-website-url}"
KEYCLOAK_URL="${KEYCLOAK_URL:-default-keycloak-url}"
REMOTE_URL="${REMOTE_URL:-http://localhost:4444/wd/hub}"
PRODUCT_SERVICE_URL="${PRODUCT_SERVICE_URL:-default-product-service-url}"
PRODUCTS_APP_URL="${PRODUCTS_APP_URL:-default-website-url}"

mkdir -p /app/test-results

STATUS_FILE="/app/test_status.txt"

java -jar \
  -Dselenide.remote="$REMOTE_URL" \
  -DPRODUCTS_APP_URL="$PRODUCTS_APP_URL" \
  -DAPP_URL="$APP_URL" \
  -DKEYCLOAK_URL="$KEYCLOAK_URL" \
  -DPRODUCT_SERVICE_URL="$PRODUCT_SERVICE_URL" \
  integration-tests.jar

JAVA_EXIT_CODE=$?

if [ $JAVA_EXIT_CODE -eq 0 ]; then
  TEST_STATUS="success"
else
  TEST_STATUS="failure"
fi

echo $TEST_STATUS > "$STATUS_FILE"

tail -f /dev/null