FROM openjdk:8-jre

COPY build/install/finrem-notification-service /opt/app/

WORKDIR /opt/app

HEALTHCHECK --interval=10s --timeout=10s --retries=10 CMD http_proxy="" curl --silent --fail http://localhost:8086/health

EXPOSE 8086

ENTRYPOINT ["/opt/app/bin/finrem-notification-service"]