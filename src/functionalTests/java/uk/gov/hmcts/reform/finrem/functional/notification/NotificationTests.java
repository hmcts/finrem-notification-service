package uk.gov.hmcts.reform.finrem.functional.notification;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import uk.gov.hmcts.reform.finrem.functional.IntegrationTestBase;

@RunWith(SerenityRunner.class)
public class NotificationTests extends IntegrationTestBase {

    private static final String NOTIFY_ASSIGN_TO_JUDGE = "/notify/assign-to-judge";
    private static final String CONSENT_ORDER_AVAILABLE = "/notify/consent-order-available";
    private static final String CONSENT_ORDER_AVAILABLE_CTSC = "/notify/consent-order-available-ctsc";
    private static final String CONSENT_ORDER_MADE = "/notify/consent-order-made";
    private static final String CONSENT_ORDER_NOT_APPROVED = "/notify/consent-order-not-approved";
    private static final String HWF_SUCCESSFUL_API_URI = "/notify/hwf-successful";
    private static final String CONTESTED_HWF_SUCCESSFUL = "/notify/contested/hwf-successful";
    private static final String CONTESTED_APPLICATION_ISSUED = "/notify/hwf-successful";
    private static final String CONTEST_ORDER_APPROVED = "/notify/hwf-successful";
    private static final String UPDATE_FRC_INFORMATION_SOL = "/notify/contested/update-frc-information";
    private static final String UPDATE_FRC_INFORMATION_COURT = "/notify/contested/update-frc-information/court";
    private static final String CONTESTED_NOTICE_OF_CHANGE = "/notify/contested/notice-of-change";
    private static final String CONSENTED_NOTICE_OF_CHANGE = "/notify/notice-of-change";
    private static final String CONTESTED_NOC_CASEWORKER = "/notify/contested/notice-of-change/caseworker";
    private static final String CONSENTED_NOC_CASEWORKER = "/notify/notice-of-change/caseworker";
    public static final String CONTESTED_REJECT_GENERAL_APPLICATION = "/notify/contested/general-application-rejected";

    @Value("${notification.uri}")
    private String notificationUrl;

    @Test
    public void verifyNotifyAssignToJudgeTestIsOkay() {
        validatePostSuccessForNotification(NOTIFY_ASSIGN_TO_JUDGE, "assignedToJudge.json");
    }

    @Test
    public void verifyNotifyConsentOrderAvailableTestIsOkay() {
        validatePostSuccessForNotification(CONSENT_ORDER_AVAILABLE, "consentOrderAvailable.json");
    }

    @Test
    public void verifyNotifyConsentOrderAvailableCtscTestIsOkay() {
        validatePostSuccessForNotification(CONSENT_ORDER_AVAILABLE_CTSC, "consentOrderAvailableCtsc.json");
    }

    @Test
    public void verifyNotifyConsentOrderMadeTestIsOkay() {
        validatePostSuccessForNotification(CONSENT_ORDER_MADE, "consentOrderMade.json");
    }

    @Test
    public void verifyNotifyConsentOrderNotApprovedTestIsOkay() {
        validatePostSuccessForNotification(CONSENT_ORDER_NOT_APPROVED, "consentOrderNotApproved.json");
    }

    @Test
    public void verifyNotifyHwfSuccessfulTestIsOkay() {
        validatePostSuccessForNotification(HWF_SUCCESSFUL_API_URI, "hwfSuccessfulEmail.json");
    }

    @Test
    public void verifyNotifyContestedHwfSuccessfulTestIsOkay() {
        validatePostSuccessForNotification(CONTESTED_HWF_SUCCESSFUL, "contestedHwfSuccessful.json");
    }

    @Test
    public void verifyNotifyContestedApplicationIssuedTestIsOkay() {
        validatePostSuccessForNotification(CONTESTED_APPLICATION_ISSUED, "contestedApplicationIssued.json");
    }

    @Test
    public void verifyNotifyContestOrderApprovedTestIsOkay() {
        validatePostSuccessForNotification(CONTEST_ORDER_APPROVED, "contestOrderApproved.json");
    }

    @Test
    public void verifyNotifyUpdateFrcInformationTestIsOkay() {
        validatePostSuccessForNotification(UPDATE_FRC_INFORMATION_SOL, "update-frc-info-sol.json");
    }

    @Test
    public void verifyNotifyUpdateFrcInformationCourtTestIsOkay() {
        validatePostSuccessForNotification(UPDATE_FRC_INFORMATION_COURT, "update-frc-info-court.json");
    }

    @Test
    public void verifyNotifyContestedNoticeOfChangeTestIsOkay() {
        validatePostSuccessForNotification(CONTESTED_NOTICE_OF_CHANGE, "contestedNoticeOfChange.json");
    }

    @Test
    public void verifyNotifyConsentedNoticeOfChangeTestIsOkay() {
        validatePostSuccessForNotification(CONSENTED_NOTICE_OF_CHANGE, "consentedNoticeOfChange.json");
    }

    @Test
    public void verifyNotifyContestedNoCAsCaseworkerTestIsOkay() {
        validatePostSuccessForNotification(CONTESTED_NOC_CASEWORKER, "contestedNoticeOfChange.json");
    }

    @Test
    public void verifyNotifyConsentedNoCAsCaseworkerTestIsOkay() {
        validatePostSuccessForNotification(CONSENTED_NOC_CASEWORKER, "consentedNoticeOfChange.json");
    }

    @Test
    public void verifyNotifyContestedRejectGeneralApplicationTestIsOkay() {
        validatePostSuccessForNotification(CONTESTED_REJECT_GENERAL_APPLICATION, "reject-general-application.json");
    }

    private void validatePostSuccessForNotification(String url, String jsonFileName) {

        SerenityRest.given()
                .relaxedHTTPSValidation()
                .headers(utils.getNewHeaders())
                .body(utils.getJsonFromFile(jsonFileName))
                .when().post(notificationUrl + url)
                .then().assertThat().statusCode(204);
    }
}
