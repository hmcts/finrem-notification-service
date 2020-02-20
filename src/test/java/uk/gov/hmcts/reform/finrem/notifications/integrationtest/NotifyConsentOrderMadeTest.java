package uk.gov.hmcts.reform.finrem.notifications.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.reform.finrem.notifications.NotificationApplication;
import uk.gov.hmcts.reform.finrem.notifications.client.EmailClient;
import uk.gov.hmcts.reform.finrem.notifications.domain.NotificationRequest;
import uk.gov.service.notify.NotificationClientException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = NotificationApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource(value = "/application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class NotifyConsentOrderMadeTest {
    private static final String AUTHORIZATION = "Authorization";
    private static final String CONSENT_ORDER_MADE = "/notify/consent-order-made";
    private static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9";
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc webClient;

    @MockBean
    private EmailClient emailClient;

    @Test
    public void givenCaseData_whenNotifyConsentOrderMade_ThenShouldSendHwfNotificationSuccessfully() throws Exception {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setNotificationEmail("test2@test.com");
        notificationRequest.setCaseReferenceNumber("EZ00110002");
        notificationRequest.setSolicitorReferenceNumber("LL03");
        notificationRequest.setName("Test");

        webClient.perform(post(CONSENT_ORDER_MADE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(notificationRequest))
                .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isNoContent());

        verify(emailClient, Mockito.times(1))
                .sendEmail(anyString(), anyString(), Mockito.anyMap(), anyString());
    }

    @Test
    public void givenCaseData_whenNotifyConsentOrderMadeAndThrowsNotificationException() throws Exception {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setNotificationEmail("test2@test.com");
        notificationRequest.setCaseReferenceNumber("EZ00110002");
        notificationRequest.setSolicitorReferenceNumber("LL03");
        when(emailClient.sendEmail(anyString(), anyString(), Mockito.anyMap(), anyString()))
                .thenThrow(new NotificationClientException(new Exception("Sending Email Failed ")));

        webClient.perform(post(CONSENT_ORDER_MADE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(notificationRequest))
                .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isNoContent());
    }


    private String convertObjectToJsonString(final Object object) {

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
