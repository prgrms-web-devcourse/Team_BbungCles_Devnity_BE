###!/bin/bash

# GitHub에 등록한 환경변수 받아오기
DOCKER_USERNAME=$1
DOCKER_REPOSITORY=$2
DOCKER_TAG_DEV=$3

# docker-compose 재실행
echo "================== PULL docker image =================="
docker pull $DOCKER_USERNAME/$DOCKER_REPOSITORY:$DOCKER_TAG_DEV

echo "================== SERVER DOWN =================="
docker-compose -p compose_dev down

echo "================== SERVER UP   =================="
docker-compose -p compose_dev up -d

echo "================== DELETE unused images  =================="
docker image prune -f


