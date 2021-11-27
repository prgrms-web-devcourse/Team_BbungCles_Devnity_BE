###!/bin/bash

EXIST_BLUE=$(docker ps | grep blue)

## 만약 blue(8081)가 구동중이 아니라면? : blue(8081)를 실행 -> green(8082)은 종료
if [ -z "$EXIST_BLUE" ]; then
    echo "@@@@@@@@@@@@@@@@@@@@@@ RUN BLUE @@@@@@@@@@@@@@@@@@@@@@"
    docker-compose -p devnity-blue -f docker-compose.blue.yml up -d

    sleep 60  # 서버가 켜지는동안 기다려준다

    echo "@@@@@@@@@@@@@@@@@@@@@@ DOWN GREEN @@@@@@@@@@@@@@@@@@@@@@"
    docker-compose -p devnity-green -f docker-compose.green.yml down
else
    echo "@@@@@@@@@@@@@@@@@@@@@@ RUN GREEN @@@@@@@@@@@@@@@@@@@@@@"
    docker-compose -p devnity-green -f docker-compose.green.yml up -d

    sleep 60

    echo "@@@@@@@@@@@@@@@@@@@@@@ DOWN BLUE @@@@@@@@@@@@@@@@@@@@@@"
    docker-compose -p devnity-blue -f docker-compose.blue.yml down
fi
