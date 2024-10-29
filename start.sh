#!/bin/bash

# 매개변수가 없는 경우 오류 메시지 출력
if [ -z "$1" ]; then
    echo "Error: No directory provided."
    echo "Usage: ./start.sh <relative-directory>"
    exit 1
fi

# 현재 디렉토리로부터의 상대 경로
DIRECTORY="$1"

# 지정된 디렉토리로 이동
if [ -d "$DIRECTORY" ]; then
    cd "$DIRECTORY" || exit
else
    echo "Error: Directory $DIRECTORY does not exist."
    exit 1
fi

# gradlew 또는 npm이 있는지 판단
if [ -f "./gradlew" ]; then
    echo "Starting Spring Boot application with gradlew..."
    ./gradlew bootRun
elif [ -f "package.json" ]; then
    echo "Starting Node.js application with npm..."
    npm run dev
else
    echo "Error: Neither gradlew nor package.json found in $DIRECTORY."
    exit 1
fi