FROM openjdk:11-jdk-slim

MAINTAINER Ravi Sankar Karuturi(github/karuturirs)

RUN apt-get update && \
    apt-get upgrade -y

# Set the working directory to /app
WORKDIR /app

COPY ./target/shipping-analyser-*.jar  /app/

CMD java -jar /app/shipping-analyser-*.jar

EXPOSE 8080