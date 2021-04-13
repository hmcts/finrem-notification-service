package uk.gov.hmcts.reform.finrem.notifications.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.finrem.notifications.NotificationApplication;

import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_ASSIGNED_TO_JUDGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENTED_GENERAL_ORDER;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_GENERAL_EMAIL;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_AVAILABLE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_AVAILABLE_CTSC;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_MADE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_NOT_APPROVED_SENT;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_HWF_SUCCESSFUL;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_TRANSFER_TO_LOCAL_COURT;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsentedNotificationController.class)
@ContextConfiguration(classes = NotificationApplication.class)
public class ConsentedNotificationControllerTest extends BaseNotificationTest {

    private static final String NOTIFY_HWF_SUCCESSFUL_URL = "/notify/hwf-successful";
    private static final String NOTIFY_ASSIGN_TO_JUDGE_URL = "/notify/assign-to-judge";
    private static final String NOTIFY_CONSENT_ORDER_MADE_URL = "/notify/consent-order-made";
    private static final String NOTIFY_CONSENT_ORDER_NOT_APPROVED_URL = "/notify/consent-order-not-approved";
    private static final String NOTIFY_CONSENT_ORDER_NOT_APPROVED_SENT_URL = "/notify/consent-order-not-approved-sent";
    private static final String NOTIFY_CONSENT_ORDER_AVAILABLE_URL = "/notify/consent-order-available";
    private static final String NOTIFY_CONSENT_ORDER_AVAILABLE_CTSC_URL = "/notify/consent-order-available-ctsc";
    private static final String NOTIFY_CONSENTED_GENERAL_ORDER_URL = "/notify/general-order";
    private static final String NOTIFY_GENERAL_EMAIL_URL = "/notify/general-email";
    private static final String NOTIFY_TRANSFER_TO_LOCAL_COURT_URL = "/notify/transfer-to-local-court";


    @Test
    public void sendEmailForHwfSuccessFul() throws Exception {
        performPostRequestWithMockContent(NOTIFY_HWF_SUCCESSFUL_URL, FR_HWF_SUCCESSFUL);
    }

    @Test
    public void sendEmailForAssignedToJudge() throws Exception {
        performPostRequestWithMockContent(NOTIFY_ASSIGN_TO_JUDGE_URL, FR_ASSIGNED_TO_JUDGE);
    }

    @Test
    public void sendEmailForConsentOrderMade() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONSENT_ORDER_MADE_URL, FR_CONSENT_ORDER_MADE);
    }

    @Test
    public void sendEmailForConsentOrderNotApproved() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONSENT_ORDER_NOT_APPROVED_URL, FR_CONSENT_ORDER_NOT_APPROVED);
    }

    @Test
    public void sendEmailForConsentOrderNotApprovedSent() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONSENT_ORDER_NOT_APPROVED_SENT_URL, FR_CONSENT_ORDER_NOT_APPROVED_SENT);
    }

    @Test
    public void sendEmailForConsentOrderAvailable() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONSENT_ORDER_AVAILABLE_URL, FR_CONSENT_ORDER_AVAILABLE);
    }

    @Test
    public void sendEmailForConsentOrderAvailableCtsc() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONSENT_ORDER_AVAILABLE_CTSC_URL, FR_CONSENT_ORDER_AVAILABLE_CTSC);
    }

    @Test
    public void sendEmailConsentedGeneralOrder() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONSENTED_GENERAL_ORDER_URL, FR_CONSENTED_GENERAL_ORDER);
    }

    @Test
    public void sendGeneralEmail() throws Exception {
        performPostRequestWithMockContent(NOTIFY_GENERAL_EMAIL_URL, FR_CONSENT_GENERAL_EMAIL);
    }

    @Test
    public void sendTransferToLocalCourtEmail() throws Exception {
        performPostRequestWithMockContent(NOTIFY_TRANSFER_TO_LOCAL_COURT_URL, FR_TRANSFER_TO_LOCAL_COURT);
    }
}
