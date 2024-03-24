docker compose down
docker volume rm $(docker volume ls -q)

docker compose build kafka-zookeeper
docker compose build kafka-broker
docker compose build mysql-db
docker compose build server
docker compose build elasticsearch
docker compose build spark-metadata
docker compose build grafana-charts
docker compose build node-ui

docker compose up -d

sleep 90
curl -s https://localhost:8888/?orgId=1