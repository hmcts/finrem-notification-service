package uk.gov.hmcts.reform.finrem.notifications.integrationtest;

import org.junit.Before;
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
import static uk.gov.hmcts.reform.finrem.notifications.NotificationConstants.AUTHORIZATION_HEADER;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.BEARER_AUTH_TOKEN;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_CASE_FAMILY_MAN_ID;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_EMAIL;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_NAME;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_REFERENCE;
import static uk.gov.hmcts.reform.finrem.notifications.testutil.ObjectMapperTestUtil.convertObjectToJsonString;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = NotificationApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PropertySource(value = "/application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class NotifyConsentOrderAvailableTest {

    private static final String CONSENT_ORDER_AVAILABLE = "/notify/consent-order-available";

    @Autowired
    private MockMvc webClient;

    @MockBean
    private EmailClient emailClient;

    private NotificationRequest notificationRequest;

    @Before
    public void setUp() {
        notificationRequest = new NotificationRequest();
        notificationRequest.setNotificationEmail(TEST_SOLICITOR_EMAIL);
        notificationRequest.setCaseReferenceNumber(TEST_CASE_FAMILY_MAN_ID);
        notificationRequest.setSolicitorReferenceNumber(TEST_SOLICITOR_REFERENCE);
        notificationRequest.setName(TEST_SOLICITOR_NAME);
    }

    @Test
    public void givenCaseData_whenNotifyConsentOrderAvailable_ThenShouldSendEmail()
            throws Exception {
        webClient.perform(post(CONSENT_ORDER_AVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(notificationRequest))
                .header(AUTHORIZATION_HEADER, BEARER_AUTH_TOKEN))
                .andExpect(status().isNoContent());

        verify(emailClient, Mockito.times(1))
                .sendEmail(anyString(), anyString(), Mockito.anyMap(), anyString());
    }

    @Test
    public void givenCaseData_whenNotifyConsentOrderAvailableAndThrowsNotificationException() throws Exception {
        when(emailClient.sendEmail(anyString(), anyString(), Mockito.anyMap(), anyString()))
                .thenThrow(new NotificationClientException(new Exception("Sending Email Failed ")));
        webClient.perform(post(CONSENT_ORDER_AVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(notificationRequest))
                .header(AUTHORIZATION_HEADER, BEARER_AUTH_TOKEN))
                .andExpect(status().isNoContent());
    }
}
