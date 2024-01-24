FROM openjdk:17-jdk as builder
WORKDIR application
COPY ./build/libs/bsafe-0.0.1-SNAPSHOT.jar application.jar
COPY ./src/main/resources/gcp-credentials.json src/main/resources/gcp-credentials.json
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:17-jdk
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ARG SPRING_PROFILES_ACTIVE=prod
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "org.springframework.boot.loader.launch.JarLauncher"]
