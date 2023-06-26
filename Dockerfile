FROM openjdk:17-jdk-slim
LABEL authors="Geovanny AlEx Rs"
VOLUME /tmp
RUN apt-get update
COPY "./target/microservices-api-gateway-0.0.1-SNAPSHOT.jar" "appgateway.jar"
ENTRYPOINT ["java", "-jar", "appgateway.jar"]
EXPOSE 8086