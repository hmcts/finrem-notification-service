package uk.gov.hmcts.reform.finrem.notifications.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmailTemplateNamesTest {

    @Test
    public void shouldReturnHWFSuccessful() {
        assertEquals("FR_HWF_SUCCESSFUL", EmailTemplateNames.FR_HWF_SUCCESSFUL.name());
    }

    @Test
    public void shouldReturnAssignToJudge() {
        assertEquals("FR_ASSIGNED_TO_JUDGE", EmailTemplateNames.FR_ASSIGNED_TO_JUDGE.name());
    }

    @Test
    public void shouldReturnConsentOrderMade() {
        assertEquals("FR_CONSENT_ORDER_MADE", EmailTemplateNames.FR_CONSENT_ORDER_MADE.name());
    }
}