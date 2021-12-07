###!/bin/bash

# GitHub에 등록한 환경변수 받아오기
DOCKER_USERNAME=$1
DOCKER_REPOSITORY=$2
DOCKER_TAG_DEV=$3
DB_URL_DEV=$4
DB_USERNAME=$5
DB_PASSWORD=$6
AWS_S3_ACCESS_KEY=$7
AWS_S3_SECRET_KEY=$8
AWS_CLOUDFRONT_URL=$9
JWT_ISSUER=$10
JWT_SECRET=$11

# 환경변수로 docker-compose 전체용 .env 파일 생성
echo "================== UPDATE '.env' file =================="
echo -e "DOCKER_USERNAME=$DOCKER_USERNAME\nDOCKER_REPOSITORY=$DOCKER_REPOSITORY\nDOCKER_TAG_DEV=$DOCKER_TAG_DEV" > .env

# docker-compose spring 컨테이너용 spring.env 파일 생성
echo "================== UPDATE 'spring.env' file =================="
echo -e "DB_URL_DEV=$DB_URL_DEV\nDB_USERNAME=$DB_USERNAME\nDB_PASSWORD=$DB_PASSWORD\nAWS_S3_ACCESS_KEY=$AWS_S3_ACCESS_KEY\nAWS_S3_SECRET_KEY=$AWS_S3_SECRET_KEY\nAWS_CLOUDFRONT_URL=$AWS_CLOUDFRONT_URL\nJWT_ISSUER=$JWT_ISSUER\nJWT_SECRET=$JWT_SECRET" > spring.env

# docker-compose 재실행
echo "================== PULL docker image =================="
docker pull $DOCKER_USERNAME/$DOCKER_REPOSITORY:$DOCKER_TAG_DEV
echo "================== SERVER DOWN =================="
docker-compose -p compose_dev down
echo "================== SERVER UP   =================="
docker-compose -p compose_dev up -d
echo "================== DELETE unused images  =================="
docker image prune -f


