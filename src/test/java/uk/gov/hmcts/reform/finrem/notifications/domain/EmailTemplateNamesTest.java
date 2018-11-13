package uk.gov.hmcts.reform.finrem.notifications.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmailTemplateNamesTest {

    @Test
    public void shouldReturnHWFSuccessful() {
        assertEquals(EmailTemplateNames.FR_HWF_SUCCESSFUL.name(), "FR_HWF_SUCCESSFUL");
    }
}