ARG APP_INSIGHTS_AGENT_VERSION=2.5.1
ARG PLATFORM=""
FROM hmctspublic.azurecr.io/base/java${PLATFORM}:11-distroless

# Mandatory!
ENV APP finrem-notification-service.jar

COPY build/libs/$APP /opt/app/
COPY lib/AI-Agent.xml /opt/app/

EXPOSE 8086

CMD ["finrem-notification-service.jar"]
