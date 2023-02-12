FROM scratch
COPY /build/native/nativeCompile/fatodo /app/fatodo

# wait tool layer
COPY ./etc/tools/wait wait
RUN chmod +x wait

# final command
CMD wait && fatodo
