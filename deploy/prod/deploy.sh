###!/bin/bash

# GitHub에 등록한 환경변수 받아오기
DOCKER_USERNAME=$1
DOCKER_REPOSITORY=$2
DOCKER_TAG_PROD=$3
DB_URL_PROD=$4
DB_USERNAME=$5
DB_PASSWORD=$6
AWS_S3_BUCKET=$7
AWS_S3_ACCESS_KEY=$8
AWS_S3_SECRET_KEY=$9

# 환경변수로 docker-compose 전체용 .env 파일 생성
echo "================== UPDATE '.env' file =================="
echo -e "DOCKER_USERNAME=$DOCKER_USERNAME\nDOCKER_REPOSITORY=$DOCKER_REPOSITORY\nDOCKER_TAG_PROD=$DOCKER_TAG_PROD" > .env

# docker-compose spring 컨테이너용 spring.env 파일 생성
echo "================== UPDATE 'spring.env' file =================="
echo -e "DB_URL_PROD=$DB_URL_PROD\nDB_USERNAME=$DB_USERNAME\nDB_PASSWORD=$DB_PASSWORD\nAWS_S3_BUCKET=$AWS_S3_BUCKET\nAWS_S3_ACCESS_KEY=$AWS_S3_ACCESS_KEY\nAWS_S3_SECRET_KEY=$AWS_S3_SECRET_KEY" > spring.env

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
