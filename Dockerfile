#FROM ghcr.io/graalvm/graalvm-ce:ol7-java17-22.3.1
#WORKDIR /build
#
#COPY build.gradle.kts gradle.properties gradlew settings.gradle.kts ./
#COPY etc etc
#COPY gradle gradle
#COPY src src
#
#RUN ./gradlew clean nativeCompile
#
#
#FROM oraclelinux:8-slim
#
#MAINTAINER Jonas Hecht
#
## Add Spring Boot Native app spring-boot-graal to Container
#COPY --from=0 /build/etc/tools/start.sh /start.sh
#COPY --from=0 /build/etc/tools/wait /wait
#COPY --from=0 /build/build/native/nativeCompile/fatodo /fatodo
#
#RUN chmod +x /start.sh
## Fire up our Spring Boot Native app by default
#CMD ["sh", "-c", "/start.sh"]


FROM oraclelinux:8-slim
#
COPY /build/native/nativeCompile/fatodo /fatodo
COPY /etc/tools/wait wait
COPY /etc/tools/start.sh start.sh
#
RUN chmod +x /start.sh
#
CMD ["sh", "-c", "/start.sh"]
