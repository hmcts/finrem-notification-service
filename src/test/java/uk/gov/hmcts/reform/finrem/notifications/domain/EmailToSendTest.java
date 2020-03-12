package uk.gov.hmcts.reform.finrem.notifications.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_EMAIL_TEMPLATE_ID;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_REFERENCE_ID;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_USER_EMAIL;

public class EmailToSendTest {
    private EmailToSend emailToSend;
    private Map<String, String> templateVars;

    @Before
    public void setUp() {
        templateVars = new HashMap<>();
        templateVars.put("abc", "123");
        emailToSend = new EmailToSend(
            TEST_USER_EMAIL,
            TEST_EMAIL_TEMPLATE_ID,
            templateVars,
            TEST_REFERENCE_ID);
    }

    @Test
    public void shouldReturnDataSetForEmailToSend() {
        assertEquals(TEST_USER_EMAIL, emailToSend.getEmailAddress());
        assertEquals(TEST_EMAIL_TEMPLATE_ID, emailToSend.getTemplateId());
        assertEquals(TEST_REFERENCE_ID, emailToSend.getReferenceId());
        assertEquals(templateVars, emailToSend.getTemplateFields());
    }
}