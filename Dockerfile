ARG APP_INSIGHTS_AGENT_VERSION=2.3.1
FROM hmctspublic.azurecr.io/base/java:openjdk-8-distroless-1.2

# Mandatory!
ENV APP finrem-notification-service.jar
ENV APPLICATION_TOTAL_MEMORY 1024M
ENV APPLICATION_SIZE_ON_DISK_IN_MB 47

COPY build/libs/$APP /opt/app/
COPY lib/AI-Agent.xml /opt/app/

HEALTHCHECK --interval=100s --timeout=100s --retries=10 CMD http_proxy="" wget -q http://localhost:8086/health || exit 1

EXPOSE 8086

CMD ["finrem-notification-service.jar"]
