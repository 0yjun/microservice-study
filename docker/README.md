# INFO

Kafka, Zookeeper, Kafka-Connector를 Docker를 통해 설정한다

## 디렉토리 구조

```
docker/
    ├── zookeeper/
        │ └── Dockerfile
    ├── kafka/
        │ └── Dockerfile
    └── kafka-connector/
        │ ├── Dockerfile
        │ └── plugins/
                │└── mysql-connector-java-x.x.x.jar
```

## 실행방법

### 1. Docker 이미지 빌드

루트 디렉토리에서 각 컴포넌트별 이미지를 빌드

```bash
# Zookeeper 이미지 빌드
docker build -t zookeeper-image ./docker/zookeeper

# Kafka Broker 이미지 빌드
docker build -t kafka-image ./docker/kafka

# Kafka-Connector 이미지 빌드
docker build -t kafka-connector-image ./docker/kafka-connector
```

### 2. docker 컨테이너 실행

# Zookeeper 컨테이너 실행

docker run -d --name zookeeper -p 2181:2181 zookeeper-image

# Kafka Broker 컨테이너 실행

docker run -d --name kafka --link zookeeper:zookeeper -p 9092:9092 kafka-image

# Kafka-Connector 컨테이너 실행

docker run -d --name kafka-connector --link kafka:kafka \
 -v $(pwd)/docker/kafka-connector/plugins:/etc/kafka-connect/jars \
 -p 8083:8083 kafka-connector-image
