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

    private void validatePostSuccessForNotification(String url, String jsonFileName) {

        SerenityRest.given()
                .relaxedHTTPSValidation()
                .headers(utils.getNewHeaders())
                .body(utils.getJsonFromFile(jsonFileName))
                .when().post(notificationUrl + url)
                .then().assertThat().statusCode(204);
    }
}
