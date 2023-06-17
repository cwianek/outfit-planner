#!/bin/bash
cd /home/debian/outfit-planner/outfit-planner-system/infrastructure/docker
docker stop outfit-planner-prod_nginx-proxy_1

chain_path=/etc/letsencrypt/live/outfitplanner.cloud/fullchain.pem
initial_hash=$(sudo md5sum $chain_path | awk '{print $1}')

sudo certbot renew

updated_hash=$(sudo md5sum $chain_path | awk '{print $1}')

if [ "$initial_hash" == "$updated_hash" ]; then
    echo "hashes are identical - starting docker"
    ./run.sh
    exit 0
fi

echo "hashes are different - installing certs"
./stop.sh
sudo cp /etc/letsencrypt/live/outfitplanner.cloud/fullchain.pem /home/debian/certs/outfitplanner.cloud.crt
sudo cp /etc/letsencrypt/live/outfitplanner.cloud/fullchain.pem /home/debian/certs/pems/fullchain.pem
mvn install -f /home/debian/outfit-planner/outfit-planner-system/pom.xml
./run.sh