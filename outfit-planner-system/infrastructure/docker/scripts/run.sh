#!/bin/bash
/usr/local/bin/docker-compose -f common.yml -f kafka_cluster.yml -f services.yml -f frontends-common.yml -f frontends-prod.yml -p outfit-planner-prod up --remove-orphans -d --build