###!/bin/bash

# GitHub에 등록한 환경변수 받아오기
DOCKER_USERNAME=$1
DOCKER_REPOSITORY=$2
DOCKER_TAG_PROD=$3

# 빌드된 최신 이미지 pull
echo "================== PULL docker image =================="
docker pull $DOCKER_USERNAME/$DOCKER_REPOSITORY:$DOCKER_TAG_PROD

## 만약 blue(8081)가 구동중이 아니라면? : blue(8081)를 실행 -> green(8082)은 종료
EXIST_BLUE=$(docker ps | grep blue)

if [ -z "$EXIST_BLUE" ]; then
    echo "================== RUN BLUE =================="
    docker-compose -p compose_blue -f docker-compose.blue.yml up -d

    sleep 60

    echo "================== DOWN GREEN =================="
    docker-compose -p compose_green -f docker-compose.green.yml down
else
    echo "================== RUN GREEN =================="
    docker-compose -p compose_green -f docker-compose.green.yml up -d

    sleep 60

    echo "================== DOWN BLUE =================="
    docker-compose -p compose_blue -f docker-compose.blue.yml down
fi

# 사용하지 않는 이미지(구 버전 이미지) 삭제
echo "================== DELETE unused images  =================="
docker image prune -f
