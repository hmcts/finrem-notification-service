package uk.gov.hmcts.reform.finrem.notifications.functionalTest;


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
import uk.gov.hmcts.reform.finrem.notifications.domain.HwfSuccessfulNotificationRequest;
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
public class NotifyHwfSuccessfulTest {
    private static final String AUTHORIZATION = "Authorization";
    private static final String HWF_SUCCESSFUL_API_URI = "/notify/hwf-successful";
    private static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9";
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc webClient;


    @MockBean
    private EmailClient emailClient;

    @Test
    public void givenCaseData_whenNotifyHwfSuccessful_ThenShouldSendHwfNotificationSuccessfully() throws Exception {
        HwfSuccessfulNotificationRequest hwfSuccessfulNotificationRequest = new HwfSuccessfulNotificationRequest();
        hwfSuccessfulNotificationRequest.setNotificationEmail("test@test.com");
        hwfSuccessfulNotificationRequest.setCaseReferenceNumber("EZ00110000");
        hwfSuccessfulNotificationRequest.setSolicitorReferenceNumber("LL02");
        hwfSuccessfulNotificationRequest.setName("Test");

        webClient.perform(post(HWF_SUCCESSFUL_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(hwfSuccessfulNotificationRequest))
                .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isNoContent());

        verify(emailClient, Mockito.times(1))
                .sendEmail(anyString(), anyString(), Mockito.anyMap(), anyString());
    }

    @Test
    public void givenCaseData_whenNotifyHwfSuccessfulAndThrowsNotificationException_ThenShouldNotSendHwfNotificationSuccessfully() throws Exception {
        HwfSuccessfulNotificationRequest hwfSuccessfulNotificationRequest = new HwfSuccessfulNotificationRequest();
        hwfSuccessfulNotificationRequest.setNotificationEmail("test@test.com");
        hwfSuccessfulNotificationRequest.setCaseReferenceNumber("EZ00110000");
        hwfSuccessfulNotificationRequest.setSolicitorReferenceNumber("LL02");
        when(emailClient.sendEmail(anyString(), anyString(), Mockito.anyMap(), anyString()))
                .thenThrow(new NotificationClientException(new Exception("Sending Email Failed ")));

        webClient.perform(post(HWF_SUCCESSFUL_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(hwfSuccessfulNotificationRequest))
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
