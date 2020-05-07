package uk.gov.hmcts.reform.finrem.notifications.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.finrem.notifications.NotificationApplication;

import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_APPLICATION_ISSUED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_HWF_SUCCESSFUL;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_PREPARE_FOR_HEARING;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTEST_ORDER_APPROVED;

@RunWith(SpringRunner.class)
@WebMvcTest(ContestedNotificationController.class)
@ContextConfiguration(classes = NotificationApplication.class)
public class ContestedNotificationControllerTest extends BaseNotificationTest {

    private static final String NOTIFY_CONTESTED_HWF_SUCCESSFUL_URL = "/notify/contested/hwf-successful";
    private static final String NOTIFY_CONTESTED_APPLICATION_ISSUED_URL = "/notify/contested/application-issued";
    private static final String NOTIFY_CONTEST_ORDER_APPROVED_URL = "/notify/contested/order-approved";
    private static final String NOTIFY_CONTESTED_PREPARE_FOR_HEARING_URL = "/notify/contested/prepare-for-hearing";

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
}