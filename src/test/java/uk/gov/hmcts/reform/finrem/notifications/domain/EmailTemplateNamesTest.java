package uk.gov.hmcts.reform.finrem.notifications.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_ASSIGNED_TO_JUDGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_AVAILABLE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_MADE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_APPLICATION_ISSUED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_CONSENT_ORDER_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_CONSENT_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_DRAFT_ORDER;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_APPLICATION_OUTCOME;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_ORDER_CONSENT;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_HWF_SUCCESSFUL;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_PREPARE_FOR_HEARING;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTEST_ORDER_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTEST_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_HWF_SUCCESSFUL;

public class EmailTemplateNamesTest {

    //consented
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

    //contested
    @Test
    public void shouldReturnConsentOrderAvailable() {
        assertEquals("FR_CONSENT_ORDER_AVAILABLE", FR_CONSENT_ORDER_AVAILABLE.name());
    }

    @Test
    public void shouldReturnContestedDraftOrder() {
        assertEquals("FR_CONTESTED_DRAFT_ORDER", FR_CONTESTED_DRAFT_ORDER.name());
    }

    @Test
    public void shouldReturnContestedHWFSuccessful() {
        assertEquals("FR_CONTESTED_HWF_SUCCESSFUL", FR_CONTESTED_HWF_SUCCESSFUL.name());
    }

    @Test
    public void shouldReturnContestedApplicationIssued() {
        assertEquals("FR_CONTESTED_APPLICATION_ISSUED", FR_CONTESTED_APPLICATION_ISSUED.name());
    }

    @Test
    public void shouldReturnContestedPrepareForHearing() {
        assertEquals("FR_CONTESTED_PREPARE_FOR_HEARING", FR_CONTESTED_PREPARE_FOR_HEARING.name());
    }

    @Test
    public void shouldReturnContestedOrderApproved() {
        assertEquals("FR_CONTEST_ORDER_APPROVED", FR_CONTEST_ORDER_APPROVED.name());
    }

    @Test
    public void shouldReturnContestedOrderNotApproved() {
        assertEquals("FR_CONTEST_ORDER_NOT_APPROVED", FR_CONTEST_ORDER_NOT_APPROVED.name());
    }

    @Test
    public void shouldReturnContestedConsentOrderApproved() {
        assertEquals("FR_CONTESTED_CONSENT_ORDER_APPROVED", FR_CONTESTED_CONSENT_ORDER_APPROVED.name());
    }

    @Test
    public void shouldReturnContestedConsentOrderNotApproved() {
        assertEquals("FR_CONTESTED_CONSENT_ORDER_NOT_APPROVED", FR_CONTESTED_CONSENT_ORDER_NOT_APPROVED.name());
    }

    @Test
    public void shouldReturnContestedGeneralOrderConsent() {
        assertEquals("FR_CONTESTED_GENERAL_ORDER_CONSENT", FR_CONTESTED_GENERAL_ORDER_CONSENT.name());
    }

    @Test
    public void shouldReturnContestedGeneralApplicationOutcome() {
        assertEquals("FR_CONTESTED_GENERAL_APPLICATION_OUTCOME", FR_CONTESTED_GENERAL_APPLICATION_OUTCOME.name());
    }
}