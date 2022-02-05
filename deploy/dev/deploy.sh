###!/bin/bash

# docker-compose 재실행
echo "================== PULL docker image =================="
docker pull $DOCKER_USERNAME/$DOCKER_REPOSITORY:$DOCKER_TAG_DEV

echo "================== SERVER DOWN =================="
docker-compose -p compose_dev down

echo "================== SERVER UP   =================="
docker-compose -p compose_dev up -d

echo "================== DELETE unused images  =================="
docker image prune -f


