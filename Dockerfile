FROM oraclelinux:8-slim

COPY /build/native/nativeCompile/fatodo /fatodo
COPY /etc/tools/wait wait
COPY /etc/tools/start.sh start.sh

RUN chmod +x /start.sh

CMD ["sh", "-c", "/start.sh"]
