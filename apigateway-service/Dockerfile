FROM openjdk:17-slim
WORKDIR /app
RUN apt-get update && apt-get install -y findutils
COPY . .
RUN chmod +x ./gradlew
CMD [ "./gradlew", "bootRun" ]