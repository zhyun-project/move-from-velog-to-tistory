FROM openjdk:19-ea-jdk-slim
VOLUME /tmp
COPY imgs imgs
COPY _h2/tistory.mv.db _h2/tistory.mv.db
COPY build/libs/tistory.jar tistory.jar
ENTRYPOINT ["java", "-jar", "tistory.jar"]
