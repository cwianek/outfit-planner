#!/bin/bash

APP_URL="${APP_URL:-default-website-url}"

java -jar integration-tests.jar $APP_URL
