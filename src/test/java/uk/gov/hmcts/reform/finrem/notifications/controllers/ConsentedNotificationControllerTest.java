package uk.gov.hmcts.reform.finrem.notifications.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.finrem.notifications.NotificationApplication;

import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_ASSIGNED_TO_JUDGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_GENERAL_EMAIL;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_AVAILABLE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_MADE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_HWF_SUCCESSFUL;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsentedNotificationController.class)
@ContextConfiguration(classes = NotificationApplication.class)
public class ConsentedNotificationControllerTest extends BaseNotificationTest {

    private static final String NOTIFY_HWF_SUCCESSFUL_URL = "/notify/hwf-successful";
    private static final String NOTIFY_ASSIGN_TO_JUDGE_URL = "/notify/assign-to-judge";
    private static final String NOTIFY_CONSENT_ORDER_MADE_URL = "/notify/consent-order-made";
    private static final String NOTIFY_CONSENT_ORDER_NOT_APPROVED_URL = "/notify/consent-order-not-approved";
    private static final String NOTIFY_CONSENT_ORDER_AVAILABLE_URL = "/notify/consent-order-available";
    private static final String NOTIFY_GENERAL_EMAIL_URL = "/notify/general-email";

    @Test
    public void sendEmailForHwfSuccessFul() throws Exception {
        sendEmailTest("/fixtures/hwfSuccessfulEmail.json", NOTIFY_HWF_SUCCESSFUL_URL, FR_HWF_SUCCESSFUL);
    }

    @Test
    public void sendEmailForAssignedToJudge() throws Exception {
        sendEmailTest("/fixtures/assignedToJudge.json", NOTIFY_ASSIGN_TO_JUDGE_URL, FR_ASSIGNED_TO_JUDGE);
    }

    @Test
    public void sendEmailForConsentOrderMade() throws Exception {
        sendEmailTest("/fixtures/consentOrderMade.json", NOTIFY_CONSENT_ORDER_MADE_URL, FR_CONSENT_ORDER_MADE);
    }

    @Test
    public void sendEmailForConsentOrderNotApproved() throws Exception {
        sendEmailTest("/fixtures/consentOrderNotApproved.json", NOTIFY_CONSENT_ORDER_NOT_APPROVED_URL, FR_CONSENT_ORDER_NOT_APPROVED);
    }

    @Test
    public void sendEmailForConsentOrderAvailable() throws Exception {
        sendEmailTest("/fixtures/consentOrderAvailable.json", NOTIFY_CONSENT_ORDER_AVAILABLE_URL, FR_CONSENT_ORDER_AVAILABLE);
    }

    @Test
    public void sendGeneralEmail() throws Exception {
        sendEmailTest("/fixtures/generalEmail.json", NOTIFY_GENERAL_EMAIL_URL, FR_CONSENT_GENERAL_EMAIL);
    }
}