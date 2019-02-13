package uk.gov.hmcts.reform.finrem.functional.notification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import uk.gov.hmcts.reform.finrem.functional.IntegrationTestBase;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SerenityRunner.class)
public class NotificationTests extends IntegrationTestBase {

    private static final String NOTIFY_ASSIGN_TO_JUDGE = "/notify/assign-to-judge";
    private static final String CONSENT_ORDER_AVAILABLE = "/notify/consent-order-available";
    private static final String CONSENT_ORDER_MADE = "/notify/consent-order-made";
    private static final String CONSENT_ORDER_NOT_APPROVED = "/notify/consent-order-not-approved";
    private static final String HWF_SUCCESSFUL_API_URI = "/notify/hwf-successful";


    @Test
    public void verifyNotifyAssignToJudgeTestIsOkay() {

        validatePostSuccessForNotification( NOTIFY_ASSIGN_TO_JUDGE,"assignToJudge.json");

    }

    @Test
    public void verifyNotifyConsentOrderAvailableTestIsOkay() {

        validatePostSuccessForNotification( CONSENT_ORDER_AVAILABLE,"consentOrderAvailable.json");

    }

    @Test
    public void verifyNotifyConsentOrderMadeTestIsOkay() {

        validatePostSuccessForNotification( CONSENT_ORDER_MADE,"consentOrderMade.json");

    }

    @Test
    public void verifyNotifyConsentOrderNotApprovedTestIsOkay() {

        validatePostSuccessForNotification( CONSENT_ORDER_NOT_APPROVED,"consentOrderNotApproved.json");

    }

    @Test
    public void verifyNotifyHwfSuccessfulTestIsOkay() {

        validatePostSuccessForNotification( HWF_SUCCESSFUL_API_URI,"hwfSuccessfulEmail.json");

    }



    private void validatePostSuccessForNotification(String url,String jsonFileName) {
        SerenityRest.given()
                .relaxedHTTPSValidation()
                .headers(utils.getNewHeaders())
                .body(utils.getJsonFromFile(jsonFileName))
                .when().post(url)
                .then().assertThat().statusCode(200);

    }


}
