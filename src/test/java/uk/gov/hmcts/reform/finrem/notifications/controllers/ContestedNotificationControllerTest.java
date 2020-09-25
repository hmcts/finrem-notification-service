package uk.gov.hmcts.reform.finrem.notifications.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.finrem.notifications.NotificationApplication;

import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_APPLICATION_ISSUED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_CONSENT_ORDER_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_CONSENT_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_DRAFT_ORDER;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_APPLICATION_OUTCOME;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_ORDER_CONSENT;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_HWF_SUCCESSFUL;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_PREPARE_FOR_HEARING;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_PREPARE_FOR_HEARING_ORDER_SENT;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTEST_ORDER_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTEST_ORDER_NOT_APPROVED;

@RunWith(SpringRunner.class)
@WebMvcTest(ContestedNotificationController.class)
@ContextConfiguration(classes = NotificationApplication.class)
public class ContestedNotificationControllerTest extends BaseNotificationTest {

    private static final String NOTIFY_CONTESTED_HWF_SUCCESSFUL_URL = "/notify/contested/hwf-successful";
    private static final String NOTIFY_CONTESTED_APPLICATION_ISSUED_URL = "/notify/contested/application-issued";
    private static final String NOTIFY_CONTEST_ORDER_APPROVED_URL = "/notify/contested/order-approved";
    private static final String NOTIFY_CONTESTED_PREPARE_FOR_HEARING_URL = "/notify/contested/prepare-for-hearing";
    private static final String NOTIFY_CONTESTED_PREPARE_FOR_HEARING_ORDER_SENT_URL = "/notify/contested/prepare-for-hearing-order-sent";
    private static final String NOTIFY_CONTESTED_DRAFT_ORDER_URL = "/notify/contested/draft-order";
    private static final String NOTIFY_CONTEST_ORDER_NOT_APPROVED_URL = "/notify/contested/order-not-approved";
    private static final String NOTIFY_CONTESTED_CONSENT_ORDER_APPROVED_URL = "/notify/contested/consent-order-approved";
    private static final String NOTIFY_CONTESTED_CONSENT_ORDER_NOT_APPROVED_URL = "/notify/contested/consent-order-not-approved";
    private static final String NOTIFY_CONTESTED_GENERAL_ORDER_CONSENT_URL = "/notify/contested/consent-general-order";
    private static final String NOTIFY_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE_URL = "/notify/contested/general-application-refer-to-judge";
    private static final String NOTIFY_CONTESTED_GENERAL_APPLICATION_OUTCOME_URL = "/notify/contested/general-application-outcome";

    @Test
    public void sendEmailForContestedHwfSuccessful() throws Exception {
        sendEmailTest("/fixtures/hwfSuccessfulEmail.json", NOTIFY_CONTESTED_HWF_SUCCESSFUL_URL, FR_CONTESTED_HWF_SUCCESSFUL);
    }

    @Test
    public void sendEmailForContestedApplicationIssued() throws Exception {
        sendEmailTest("/fixtures/contestedApplicationIssued.json", NOTIFY_CONTESTED_APPLICATION_ISSUED_URL, FR_CONTESTED_APPLICATION_ISSUED);
    }

    @Test
    public void sendEmailForContestOrderApproved() throws Exception {
        sendEmailTest("/fixtures/contestOrderApproved.json", NOTIFY_CONTEST_ORDER_APPROVED_URL, FR_CONTEST_ORDER_APPROVED);
    }

    @Test
    public void sendEmailForPrepareForHearing() throws Exception {
        sendEmailTest("/fixtures/prepareForHearing.json", NOTIFY_CONTESTED_PREPARE_FOR_HEARING_URL, FR_CONTESTED_PREPARE_FOR_HEARING);
    }

    @Test
    public void sendEmailForPrepareForHearingOrderSent() throws Exception {
        sendEmailTest("/fixtures/prepareForHearingOrderSent.json", NOTIFY_CONTESTED_PREPARE_FOR_HEARING_ORDER_SENT_URL,
                FR_CONTESTED_PREPARE_FOR_HEARING_ORDER_SENT);
    }

    @Test
    public void sendEmailForContestedDraftOrder() throws Exception {
        sendEmailTest("/fixtures/contestedDraftOrder.json", NOTIFY_CONTESTED_DRAFT_ORDER_URL, FR_CONTESTED_DRAFT_ORDER);
    }

    @Test
    public void sendEmailForContestOrderNotApproved() throws Exception {
        sendEmailTest("/fixtures/contestOrderNotApproved.json", NOTIFY_CONTEST_ORDER_NOT_APPROVED_URL, FR_CONTEST_ORDER_NOT_APPROVED);
    }

    @Test
    public void sendEmailForConsentOrderApproved() throws Exception {
        sendEmailTest("/fixtures/contestedConsentOrderApproved.json", NOTIFY_CONTESTED_CONSENT_ORDER_APPROVED_URL,
            FR_CONTESTED_CONSENT_ORDER_APPROVED);
    }

    @Test
    public void sendEmailForConsentOrderNotApproved() throws Exception {
        sendEmailTest("/fixtures/contestedConsentOrderNotApproved.json", NOTIFY_CONTESTED_CONSENT_ORDER_NOT_APPROVED_URL,
            FR_CONTESTED_CONSENT_ORDER_NOT_APPROVED);
    }

    @Test
    public void sendEmailContestedGeneralOrderConsent() throws Exception {
        sendEmailTest("/fixtures/contestedGeneralOrderConsent.json", NOTIFY_CONTESTED_GENERAL_ORDER_CONSENT_URL,
                FR_CONTESTED_GENERAL_ORDER_CONSENT);
    }

    @Test
    public void sendEmailContestedGeneralApplicationReferToJudge() throws Exception {
        sendEmailTest("/fixtures/contestedGeneralApplicationReferToJudge.json", NOTIFY_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE_URL,
                FR_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE);
    }

    @Test
    public void sendEmailContestedGeneralApplicationOutcome() throws Exception {
        sendEmailTest("/fixtures/contestedGeneralApplicationOutcome.json", NOTIFY_CONTESTED_GENERAL_APPLICATION_OUTCOME_URL,
                FR_CONTESTED_GENERAL_APPLICATION_OUTCOME);
    }
}