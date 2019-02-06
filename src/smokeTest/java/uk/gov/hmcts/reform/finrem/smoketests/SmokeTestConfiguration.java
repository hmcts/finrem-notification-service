package uk.gov.hmcts.reform.finrem.smoketests;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("uk.gov.hmcts.reform.finrem.smoketests")
@PropertySource("application.properties")
public class SmokeTestConfiguration {
}
