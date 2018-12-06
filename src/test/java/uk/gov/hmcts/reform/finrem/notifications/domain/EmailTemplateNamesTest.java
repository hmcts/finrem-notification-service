package uk.gov.hmcts.reform.finrem.notifications.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_ASSIGNED_TO_JUDGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_APPROVED_AVAILABLE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_AVAILABLE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_MADE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_HWF_SUCCESSFUL;

public class EmailTemplateNamesTest {

    @Test
    public void shouldReturnHWFSuccessful() {
        assertEquals("FR_HWF_SUCCESSFUL", FR_HWF_SUCCESSFUL.name());
    }

    @Test
    public void shouldReturnAssignToJudge() {
        assertEquals("FR_ASSIGNED_TO_JUDGE", FR_ASSIGNED_TO_JUDGE.name());
    }

    @Test
    public void shouldReturnConsentOrderMade() {
        assertEquals("FR_CONSENT_ORDER_MADE", FR_CONSENT_ORDER_MADE.name());
    }

    @Test
    public void shouldReturnConsentOrderNotApproved() {
        assertEquals("FR_CONSENT_ORDER_NOT_APPROVED", FR_CONSENT_ORDER_NOT_APPROVED.name());
    }

    @Test
    public void shouldReturnConsentOrderAvailable() {
        assertEquals("FR_CONSENT_ORDER_AVAILABLE", FR_CONSENT_ORDER_AVAILABLE.name());
    }

    @Test
    public void shouldReturnConsentOrderApprovedAndAvailable() {
        assertEquals("FR_CONSENT_ORDER_APPROVED_AVAILABLE", FR_CONSENT_ORDER_APPROVED_AVAILABLE.name());
    }
}