package uk.gov.hmcts.reform.finrem.notifications.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.finrem.notifications.NotificationApplication;

import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_APPLICATION_ISSUED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_DRAFT_ORDER;
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

}