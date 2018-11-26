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
import uk.gov.hmcts.reform.finrem.notifications.domain.NotificationRequest;
import uk.gov.service.notify.NotificationClientException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_ASSIGNED_TO_JUDGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_MADE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_HWF_SUCCESSFUL;

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

    private NotificationRequest notificationRequest;

    @Before
    public void setUp() {
        notificationRequest = new NotificationRequest();
        notificationRequest.setNotificationEmail(EMAIL_ADDRESS);
        notificationRequest.setCaseReferenceNumber("12345");
        notificationRequest.setSolicitorReferenceNumber("56789");
        notificationRequest.setName("Padmaja Ramisetti");
    }

    @Test
    public void sendHwfSuccessfulConfirmationEmailShouldCallTheEmailClientToSendAnEmail()
            throws NotificationClientException {
        Map<String, String> expectedEmailTemplateVars = getEmailTemplateVars();
        expectedEmailTemplateVars.putAll(emailTemplateVars.get(FR_HWF_SUCCESSFUL.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_HWF_SUCCESSFUL);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_HWF_SUCCESSFUL.name())),
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
            emailService.sendConfirmationEmail(notificationRequest, FR_HWF_SUCCESSFUL);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void sendAssignedToJudgeConfirmationEmailShouldCallTheEmailClientToSendAnEmail()
            throws NotificationClientException {
        Map<String, String> expectedEmailTemplateVars = getEmailTemplateVars();
        expectedEmailTemplateVars.putAll(emailTemplateVars.get(FR_ASSIGNED_TO_JUDGE.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_ASSIGNED_TO_JUDGE);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_ASSIGNED_TO_JUDGE.name())),
                eq(EMAIL_ADDRESS),
                eq(expectedEmailTemplateVars),
                anyString());
    }

    @Test
    public void sendAssignedToJudgeConfirmationEmailShouldNotPropagateNotificationClientException()
            throws NotificationClientException {
        doThrow(new NotificationClientException(new Exception("Exception inception")))
                .when(mockClient).sendEmail(anyString(), anyString(), eq(null), anyString());
        try {
            emailService.sendConfirmationEmail(notificationRequest, FR_ASSIGNED_TO_JUDGE);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void sendConsentOrderMadeConfirmationEmailShouldCallTheEmailClientToSendAnEmail()
            throws NotificationClientException {
        Map<String, String> expectedEmailTemplateVars = getEmailTemplateVars();
        expectedEmailTemplateVars.putAll(emailTemplateVars.get(FR_CONSENT_ORDER_MADE.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_MADE);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_CONSENT_ORDER_MADE.name())),
                eq(EMAIL_ADDRESS),
                eq(expectedEmailTemplateVars),
                anyString());
    }

    @Test
    public void sendConsentOrderMadeConfirmationEmailShouldNotPropagateNotificationClientException()
            throws NotificationClientException {
        doThrow(new NotificationClientException(new Exception("Exception inception")))
                .when(mockClient).sendEmail(anyString(), anyString(), eq(null), anyString());
        try {
            emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_MADE);
        } catch (Exception e) {
            fail();
        }
    }

    private Map<String, String> getEmailTemplateVars() {
        Map<String, String> expectedEmailTemplateVars = new HashMap<>();
        expectedEmailTemplateVars.put("caseReferenceNumber", notificationRequest.getCaseReferenceNumber());
        expectedEmailTemplateVars.put("solicitorReferenceNumber", notificationRequest.getSolicitorReferenceNumber());
        expectedEmailTemplateVars.put("name", notificationRequest.getName());
        return expectedEmailTemplateVars;
    }
}