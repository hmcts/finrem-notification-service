package uk.gov.hmcts.reform.finrem.notification.smoketests;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("uk.gov.hmcts.reform.finrem.notification")
@PropertySource("application.properties")
public class SmokeTestConfiguration {
}
