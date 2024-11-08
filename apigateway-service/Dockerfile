FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY . .
RUN chmod 775 ./gradlew
CMD [ "./gradlew", "bootRun" ]