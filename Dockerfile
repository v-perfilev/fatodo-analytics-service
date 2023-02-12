FROM alpine:3.17
COPY /build/native/nativeCompile/fatodo /app/fatodo

# wait tool layer
COPY ./etc/tools/wait wait
RUN chmod +x wait

# final command
CMD wait && fatodo
