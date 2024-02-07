#!/bin/bash

APP_URL="${APP_URL:-default-website-url}"
REMOTE_URL="${REMOTE_URL:-http://localhost:4444/wd/hub}"

mkdir -p /app/test-results

LOG_FILE="/app/test-results/test_logs.txt"

java -jar -Dselenide.remote=$REMOTE_URL integration-tests.jar $APP_URL 2>&1 | tee "$LOG_FILE"
