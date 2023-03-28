package uk.gov.hmcts.reform.finrem.notifications.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.finrem.notifications.NotificationApplication;

import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_BARRISTER_ACCESS_ADDED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_BARRISTER_ACCESS_REMOVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_APPLICATION_ISSUED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_CONSENT_ORDER_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_CONSENT_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_DRAFT_ORDER;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_APPLICATION_OUTCOME;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_EMAIL;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_ORDER;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_ORDER_CONSENT;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_HWF_SUCCESSFUL;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_INTERIM_HEARING;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_NOC_CASEWORKER;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_NOTICE_OF_CHANGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_PREPARE_FOR_HEARING;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_UPDATE_FRC_COURT;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_UPDATE_FRC_SOL;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTEST_ORDER_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTEST_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_REJECT_GENERAL_APPLICATION;

@RunWith(SpringRunner.class)
@WebMvcTest(ContestedNotificationController.class)
@ContextConfiguration(classes = NotificationApplication.class)
public class ContestedNotificationControllerTest extends BaseNotificationTest {

    private static final String NOTIFY_CONTESTED_HWF_SUCCESSFUL_URL = "/notify/contested/hwf-successful";
    private static final String NOTIFY_CONTESTED_APPLICATION_ISSUED_URL = "/notify/contested/application-issued";
    private static final String NOTIFY_CONTEST_ORDER_APPROVED_URL = "/notify/contested/order-approved";
    private static final String NOTIFY_CONTESTED_PREPARE_FOR_HEARING_URL = "/notify/contested/prepare-for-hearing";
    private static final String NOTIFY_CONTESTED_DRAFT_ORDER_URL = "/notify/contested/draft-order";
    private static final String NOTIFY_CONTEST_ORDER_NOT_APPROVED_URL = "/notify/contested/order-not-approved";
    private static final String NOTIFY_CONTESTED_CONSENT_ORDER_APPROVED_URL = "/notify/contested/consent-order-approved";
    private static final String NOTIFY_CONTESTED_CONSENT_ORDER_NOT_APPROVED_URL = "/notify/contested/consent-order-not-approved";
    private static final String NOTIFY_CONTESTED_GENERAL_ORDER_CONSENT_URL = "/notify/contested/consent-general-order";
    private static final String NOTIFY_CONTESTED_GENERAL_ORDER_URL = "/notify/contested/general-order";
    private static final String NOTIFY_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE_URL = "/notify/contested/general-application-refer-to-judge";
    private static final String NOTIFY_CONTESTED_GENERAL_APPLICATION_OUTCOME_URL = "/notify/contested/general-application-outcome";
    private static final String NOTIFY_CONTESTED_GENERAL_EMAIL_URL = "/notify/contested/general-email";
    private static final String NOTIFY_CONTESTED_INTERIM_HEARING_URL = "/notify/contested/prepare-for-interim-hearing-sent";
    private static final String NOTIFY_CONTESTED_UPDATE_FRC_INFO = "/notify/contested/update-frc-information";
    private static final String NOTIFY_CONTESTED_UPDATE_FRC_COURT = "/notify/contested/update-frc-information/court";
    private static final String NOTIFY_CONTESTED_NOTICE_OF_CHANGE = "/notify/contested/notice-of-change";
    private static final String NOTIFY_CONTESTED_NOC_CASEWORKER = "/notify/contested/notice-of-change/caseworker";
    private static final String NOTIFY_CONTESTED_REJECTED_GENERAL_APPLICATION = "/notify/contested/general-application-rejected";
    private static final String NOTIFY_CONTESTED_BARRISTER_ACCESS_ADDED = "/notify/contested/barrister-access-added";
    private static final String NOTIFY_CONTESTED_BARRISTER_ACCESS_REMOVED = "/notify/contested/barrister-access-removed";



    @Test
    public void sendEmailForContestedHwfSuccessful() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_HWF_SUCCESSFUL_URL, FR_CONTESTED_HWF_SUCCESSFUL);
    }

    @Test
    public void sendEmailForContestedApplicationIssued() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_APPLICATION_ISSUED_URL, FR_CONTESTED_APPLICATION_ISSUED);
    }

    @Test
    public void sendEmailForContestOrderApproved() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTEST_ORDER_APPROVED_URL, FR_CONTEST_ORDER_APPROVED);
    }

    @Test
    public void sendEmailForPrepareForHearing() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_PREPARE_FOR_HEARING_URL, FR_CONTESTED_PREPARE_FOR_HEARING);
    }

    @Test
    public void sendEmailForContestedDraftOrder() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_DRAFT_ORDER_URL, FR_CONTESTED_DRAFT_ORDER);
    }

    @Test
    public void sendEmailForContestOrderNotApproved() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTEST_ORDER_NOT_APPROVED_URL, FR_CONTEST_ORDER_NOT_APPROVED);
    }

    @Test
    public void sendEmailForConsentOrderApproved() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_CONSENT_ORDER_APPROVED_URL, FR_CONTESTED_CONSENT_ORDER_APPROVED);
    }

    @Test
    public void sendEmailForConsentOrderNotApproved() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_CONSENT_ORDER_NOT_APPROVED_URL, FR_CONTESTED_CONSENT_ORDER_NOT_APPROVED);
    }

    @Test
    public void sendEmailContestedGeneralOrderConsent() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_GENERAL_ORDER_CONSENT_URL, FR_CONTESTED_GENERAL_ORDER_CONSENT);
    }

    @Test
    public void sendEmailContestedGeneralOrder() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_GENERAL_ORDER_URL, FR_CONTESTED_GENERAL_ORDER);
    }

    @Test
    public void sendEmailContestedGeneralApplicationReferToJudge() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE_URL, FR_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE);
    }

    @Test
    public void sendEmailContestedGeneralApplicationOutcome() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_GENERAL_APPLICATION_OUTCOME_URL, FR_CONTESTED_GENERAL_APPLICATION_OUTCOME);
    }

    @Test
    public void sendGeneralEmail() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_GENERAL_EMAIL_URL, FR_CONTESTED_GENERAL_EMAIL);
    }

    @Test
    public void sendEmailForPrepareForInterimHearingSent() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_INTERIM_HEARING_URL, FR_CONTESTED_INTERIM_HEARING);
    }

    @Test
    public void givenContestedCase_whenUpdateFrcInformation_thenSolicitorNotificationEmailSent() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_UPDATE_FRC_INFO, FR_CONTESTED_UPDATE_FRC_SOL);
    }

    @Test
    public void givenContestedCase_whenUpdateFrcInformation_thenCourtNotificationEmailSent() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_UPDATE_FRC_COURT, FR_CONTESTED_UPDATE_FRC_COURT);
    }

    @Test
    public void givenContestedCaseWhenNoticeOfChangeRequestThenContestedNoticeOfChangeConfirmationEmailIsSent() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_NOTICE_OF_CHANGE, FR_CONTESTED_NOTICE_OF_CHANGE);
    }

    @Test
    public void givenContestedCase_whenNocRequestAsCaseworker_thenContestedNocEmailSent() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_NOC_CASEWORKER, FR_CONTESTED_NOC_CASEWORKER);
    }

    @Test
    public void givenContestedCase_whenRejectedGeneralApplication_thenEmailSent() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_REJECTED_GENERAL_APPLICATION, FR_REJECT_GENERAL_APPLICATION);
    }

    @Test
    public void givenContestedCase_whenBarristerAccessAdded_thenEmailSent() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_BARRISTER_ACCESS_ADDED, FR_BARRISTER_ACCESS_ADDED);
    }

    @Test
    public void givenContestedCase_whenBarristerAccessRemoved_thenEmailSent() throws Exception {
        performPostRequestWithMockContent(NOTIFY_CONTESTED_BARRISTER_ACCESS_REMOVED, FR_BARRISTER_ACCESS_REMOVED);
    }
}
