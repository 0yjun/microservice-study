#!/bin/bash

# Gradle 빌드 (JAR 파일 생성)
./gradlew clean build

# 빌드 성공 여부 확인
if [ $? -ne 0 ]; then
  echo "Gradle 빌드에 실패했습니다."
  exit 1
fi

# 생성된 JAR 파일 경로 설정
JAR_FILE=$(find build/libs -name "*.jar" | head -n 1)

# JAR 파일 존재 여부 확인
if [ -z "$JAR_FILE" ]; then
  echo "JAR 파일을 찾을 수 없습니다."
  exit 1
fi

# JAR 파일 실행
echo "실행 중: $JAR_FILE"
java -jar "$JAR_FILE"