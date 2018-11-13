package uk.gov.hmcts.reform.finrem.notifications.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.finrem.notifications.NotificationApplication;
import uk.gov.hmcts.reform.finrem.notifications.client.EmailClient;
import uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames;
import uk.gov.hmcts.reform.finrem.notifications.domain.HwfSuccessfulNotificationRequest;
import uk.gov.service.notify.NotificationClientException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NotificationApplication.class)
@TestPropertySource(locations = "/application.properties")
public class EmailServiceTest {
    private static final String EMAIL_ADDRESS = "simulate-delivered@notifications.service.gov.uk";

    @MockBean
    private EmailClient mockClient;

    @Autowired
    private EmailService emailService;

    @Value("#{${uk.gov.notify.email.templates}}")
    private Map<String, String> emailTemplates;

    @Value("#{${uk.gov.notify.email.template.vars}}")
    private Map<String, Map<String, String>> emailTemplateVars;

    private HwfSuccessfulNotificationRequest notificationRequest;

    @Before
    public void setUp() {
        notificationRequest = new HwfSuccessfulNotificationRequest();
        notificationRequest.setNotificationEmail(EMAIL_ADDRESS);
        notificationRequest.setCaseReferenceNumber("12345");
        notificationRequest.setSolicitorReferenceNumber("56789");
        notificationRequest.setName("Padmaja Ramisetti");
    }

    @Test
    public void sendHwfSuccessfulConfirmationEmailShouldCallTheEmailClientToSendAnEmail()
            throws NotificationClientException {
        Map<String, String> expectedEmailTemplateVars = new HashMap<>();
        expectedEmailTemplateVars.put("caseReferenceNumber",notificationRequest.getCaseReferenceNumber());
        expectedEmailTemplateVars.put("solicitorReferenceNumber", notificationRequest.getSolicitorReferenceNumber());
        expectedEmailTemplateVars.put("name", notificationRequest.getName());
        expectedEmailTemplateVars.putAll(emailTemplateVars.get(EmailTemplateNames.FR_HWF_SUCCESSFUL.name()));

        emailService.sendHwfSuccessfulConfirmationEmail(notificationRequest);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(EmailTemplateNames.FR_HWF_SUCCESSFUL.name())),
                eq(EMAIL_ADDRESS),
                eq(expectedEmailTemplateVars),
                anyString());
    }

    @Test
    public void sendHwfSuccessfulConfirmationEmailShouldNotPropagateNotificationClientException()
            throws NotificationClientException {
        doThrow(new NotificationClientException(new Exception("Exception inception")))
                .when(mockClient).sendEmail(anyString(), anyString(), eq(null), anyString());

        try {
            emailService.sendHwfSuccessfulConfirmationEmail(notificationRequest);
        } catch (Exception e) {
            fail();
        }
    }
}