package uk.gov.hmcts.reform.finrem.notifications.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailToSendTest {
    private EmailToSend emailToSend;
    private Map<String, String> templateVars;

    @BeforeEach
    public void setUp() {
        templateVars = new HashMap<>();
        templateVars.put("abc", "123");
        emailToSend = new EmailToSend("test@test.com", "12345",
                templateVars, "referenceId");
    }

    @Test
    public void shouldReturnDataSetForEmailToSend() {
        assertEquals("test@test.com", emailToSend.getEmailAddress());
        assertEquals("12345", emailToSend.getTemplateId());
        assertEquals("referenceId", emailToSend.getReferenceId());
        assertEquals(templateVars, emailToSend.getTemplateFields());
    }
}