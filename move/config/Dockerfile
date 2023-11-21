FROM openjdk:19-ea-jdk-slim
VOLUME /tmp
COPY build/libs/config.jar ConfigServer.jar
ENTRYPOINT ["java", "-jar", "ConfigServer.jar"]
