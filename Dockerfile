FROM busybox
COPY ./target/xmas /root/
ENTRYPOINT ["/root/xmas"]
