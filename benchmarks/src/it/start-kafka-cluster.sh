#!/usr/bin/env bash

function create_topic {
    TOPIC_NAME=$1
    PARTITIONS=$2
    REPLICATION_FACTOR=$3
    docker-compose -f ./docker-compose.yml exec -T broker kafka-topics --create --topic ${TOPIC_NAME} --partitions ${TOPIC_PARTITIONS} --replication-factor 1 --if-not-exists --zookeeper zookeper:2181
}

echo "Starting Kafka Cluster..."
docker-compose -f ./docker-compose.yml up -d zookeeper broker


docker ps

sleep 5

create_topic topic_1P_1RF 1 1