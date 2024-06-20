#!/bin/bash

# As a first thing, we stop and remove existing containers
# and then destroy related volumes.

docker compose down --volumes

# Then we build Docker images
# and start Docker containers.

docker compose build kafka-zookeeper
docker compose build kafka-broker
docker compose build mysql-db
docker compose build data-manager
docker compose build api-gateway
docker compose build elasticsearch
docker compose build spark-metadata
docker compose build grafana-charts
docker compose build user-interface

docker compose up -d

# Finally, we wait for services to start and download all dependencies.
# After that, we restart Grafana to complete the extensions installation.

sleep 90
docker restart grafana-charts
sleep 40
curl -s https://localhost:3000/?orgId=1