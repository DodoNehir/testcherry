#!/bin/bash

# 변수 설정
PROJECT_NAME="testcherry" # 프로젝트 이름
IMAGE_NAME="nehirkim/kiraz:amd64" # Docker 이미지 이름 (Docker Hub 사용자 이름 포함)
JAR_FILE="build/libs/$PROJECT_NAME-*.jar" # 생성된 JAR 파일 경로

# 1. JAR 파일 빌드
echo "JAR 파일 빌드 시작..."
./gradlew clean build # Gradle 빌드 명령
if [ $? -ne 0 ]; then
  echo "JAR 파일 빌드 실패!"
  exit 1
fi
echo "JAR 파일 빌드 완료."

# 2. Docker 이미지 빌드
echo "Docker 이미지 빌드 시작..."
docker build --platform linux/amd64 -t $IMAGE_NAME .
if [ $? -ne 0 ]; then
  echo "Docker 이미지 빌드 실패!"
  exit 1
fi
echo "Docker 이미지 빌드 완료."


# 4. Docker 이미지 푸시
echo "Docker 이미지 푸시 시작..."
docker push $IMAGE_NAME
if [ $? -ne 0 ]; then
  echo "Docker 이미지 푸시 실패!"
  exit 1
fi
echo "Docker 이미지 푸시 완료."

echo "빌드 및 푸시 완료!"