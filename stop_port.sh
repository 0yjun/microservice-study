#!/bin/bash

# 매개변수가 없는 경우 오류 메시지 출력
if [ $# -eq 0 ]; then
    echo "Error: No port numbers provided."
    echo "Usage: ./stop_port.sh <port1> <port2> <port3> ..."
    exit 1
fi

# 모든 매개변수에 대해 반복
for PORT in "$@"; do
    # 해당 포트에서 실행 중인 PID 찾기
    PIDS=$(lsof -t -i:"$PORT")

    # PID가 없는 경우 오류 메시지 출력
    if [ -z "$PIDS" ]; then
        echo "No running process found on port: $PORT"
    else
        # 각 PID에 대해 종료 명령어 실행
        for PID in $PIDS; do
            echo "Stopping process on port $PORT with PID: $PID"
            kill "$PID"
        done
    fi
done

echo "Processes on specified ports have been stopped."