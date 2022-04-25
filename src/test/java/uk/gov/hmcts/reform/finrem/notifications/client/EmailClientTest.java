package uk.gov.hmcts.reform.finrem.notifications.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class EmailClientTest {

    @Value("${uk.gov.notify.api.key}")
    private String apiKey;

    @Autowired
    private EmailClient emailClient;

    @Test
    public void shouldHaveTheCorrectApiKey() {
        assertEquals(apiKey, emailClient.getApiKey());
    }
}