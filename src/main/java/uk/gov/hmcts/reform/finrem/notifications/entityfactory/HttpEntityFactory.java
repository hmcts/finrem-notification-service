package uk.gov.hmcts.reform.finrem.notifications.entityfactory;

import org.springframework.http.HttpEntity;

public interface HttpEntityFactory {
    HttpEntity<Object> createRequestEntityForHealthCheck();
}
