#!/bin/bash

APP_URL="${APP_URL:-default-website-url}"
REMOTE_URL="${REMOTE_URL:-http://localhost:4444/wd/hub}"

java -jar -Dselenide.remote=$REMOTE_URL integration-tests.jar $APP_URL
