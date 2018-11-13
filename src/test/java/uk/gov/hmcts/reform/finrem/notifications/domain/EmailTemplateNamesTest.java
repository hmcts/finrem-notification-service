package uk.gov.hmcts.reform.finrem.notifications.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmailTemplateNamesTest {

    @Test
    public void shouldReturnHWFSuccessful() {
        assertEquals("FR_HWF_SUCCESSFUL", EmailTemplateNames.FR_HWF_SUCCESSFUL.name());
    }
}