package uk.gov.hmcts.reform.finrem.notifications.functionalTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.reform.finrem.notifications.NotificationApplication;
import uk.gov.hmcts.reform.finrem.notifications.client.EmailClient;
import uk.gov.hmcts.reform.finrem.notifications.domain.HwfSuccessfulNotificationRequest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = NotificationApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource(value = "classpath:application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class NotifyHwfSuccessfulTest {
    private static final String AUTHORIZATION = "Authorization";
    private static final String HWF_SUCCESSFUL_API_URI = "/notify/hwf-successful";
    private static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9";
    private static final String EMAIL_CONTEXT_PATH = "https://api.notifications.service.gov.uk";
    private static ObjectMapper objectMapper = new ObjectMapper();

    @ClassRule
    public static WireMockClassRule emailServiceServer = new WireMockClassRule(9999);

    @Autowired
    private MockMvc webClient;

    @Autowired
    private EmailClient emailClient;

    @Test
    public void givenCaseData_whenNotifyHwfSuccessful_ThenShouldSendHwfNotificationSuccessfully() throws Exception {
        HwfSuccessfulNotificationRequest hwfSuccessfulNotificationRequest = new HwfSuccessfulNotificationRequest();
        hwfSuccessfulNotificationRequest.setNotificationEmail("test@test.com");
        hwfSuccessfulNotificationRequest.setCaseReferenceNumber("EZ00110000");
        hwfSuccessfulNotificationRequest.setSolicitorReferenceNumber("LL02");
        hwfSuccessfulNotificationRequest.setName("Test");
        stubEmailServerEndpoint(HttpStatus.OK, "Success");

        webClient.perform(post(HWF_SUCCESSFUL_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(hwfSuccessfulNotificationRequest))
                .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isNoContent());
    }

    private void stubEmailServerEndpoint(HttpStatus status, String body)
            throws Exception {
        emailServiceServer.stubFor(WireMock.post(EMAIL_CONTEXT_PATH)
                .willReturn(aResponse()
                        .withStatus(status.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE)
                        .withBody(body)));
    }

    private String convertObjectToJsonString(final Object object) {

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
