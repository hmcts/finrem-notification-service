package uk.gov.hmcts.reform.finrem.notifications.service;

import lombok.extern.slf4j.Slf4j;
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
import static org.springframework.util.StringUtils.isEmpty;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_CASE_FAMILY_MAN_ID;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_EMAIL;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_NAME;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_REFERENCE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_ASSIGNED_TO_JUDGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_AVAILABLE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_MADE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_HWF_SUCCESSFUL;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NotificationApplication.class)
@TestPropertySource(locations = "/application.properties")
@Slf4j
public class EmailServiceTest {

    @MockBean
    private EmailClient mockClient;

    @Autowired
    private EmailService emailService;

    @Value("#{${uk.gov.notify.email.templates}}")
    private Map<String, String> emailTemplates;

    @Value("#{${uk.gov.notify.email.template.vars}}")
    private Map<String, Map<String, String>> emailTemplateVars;

    @Value("#{${uk.gov.notify.email.contestedContactEmails}}")
    private Map<String, Map<String, String>> contestedContactEmails;

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
    public void sendHwfSuccessfulConfirmationEmailShouldCallTheEmailClientToSendAnEmail()
            throws NotificationClientException {
        Map<String, String> expectedEmailTemplateVars = getEmailTemplateVars();
        expectedEmailTemplateVars.putAll(emailTemplateVars.get(FR_HWF_SUCCESSFUL.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_HWF_SUCCESSFUL);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_HWF_SUCCESSFUL.name())),
                eq(TEST_SOLICITOR_EMAIL),
                eq(expectedEmailTemplateVars),
                anyString());
    }

    @Test
    public void sendHwfSuccessfulConfirmationEmailContested()
            throws NotificationClientException {
        setContestedData();
        Map<String, String> expectedEmailTemplateVars = getEmailTemplateVars();
        expectedEmailTemplateVars.putAll(emailTemplateVars.get(FR_HWF_SUCCESSFUL.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_HWF_SUCCESSFUL);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_HWF_SUCCESSFUL.name())),
                eq(TEST_SOLICITOR_EMAIL),
                eq(expectedEmailTemplateVars),
                anyString());
    }

    private void setContestedData() {
        notificationRequest.setSelectedCourt("nottingham");
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
                eq(TEST_SOLICITOR_EMAIL),
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
                eq(TEST_SOLICITOR_EMAIL),
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

    @Test
    public void sendConsentOrderNotApprovedEmailShouldCallTheEmailClientToSendAnEmail()
            throws NotificationClientException {

        Map<String, String> expectedEmailTemplateVars = getEmailTemplateVars();
        expectedEmailTemplateVars.putAll(emailTemplateVars.get(FR_CONSENT_ORDER_NOT_APPROVED.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_NOT_APPROVED);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_CONSENT_ORDER_NOT_APPROVED.name())),
                eq(TEST_SOLICITOR_EMAIL),
                eq(expectedEmailTemplateVars),
                anyString());
    }

    @Test
    public void sendConsentOrderNotApprovedEmailShouldNotPropagateNotificationClientException()
            throws NotificationClientException {

        doThrow(new NotificationClientException(new Exception("Exception inception")))
                .when(mockClient).sendEmail(anyString(), anyString(), eq(null), anyString());
        try {
            emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_NOT_APPROVED);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void sendConsentOrderAvailableEmailShouldCallTheEmailClientToSendAnEmail()
            throws NotificationClientException {

        Map<String, String> expectedEmailTemplateVars = getEmailTemplateVars();
        expectedEmailTemplateVars.putAll(emailTemplateVars.get(FR_CONSENT_ORDER_AVAILABLE.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_AVAILABLE);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_CONSENT_ORDER_AVAILABLE.name())),
                eq(TEST_SOLICITOR_EMAIL),
                eq(expectedEmailTemplateVars),
                anyString());
    }

    @Test
    public void sendConsentOrderAvailableEmailShouldNotPropagateNotificationClientException()
            throws NotificationClientException {
        doThrow(new NotificationClientException(new Exception("Exception inception")))
                .when(mockClient).sendEmail(anyString(), anyString(), eq(null), anyString());
        try {
            emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_AVAILABLE);
        } catch (Exception e) {
            fail();
        }
    }

    private Map<String, String> getEmailTemplateVars() {
        Map<String, String> expectedEmailTemplateVars = new HashMap<>();
        expectedEmailTemplateVars.put("caseReferenceNumber", notificationRequest.getCaseReferenceNumber());
        expectedEmailTemplateVars.put("solicitorReferenceNumber", notificationRequest.getSolicitorReferenceNumber());
        expectedEmailTemplateVars.put("name", notificationRequest.getName());

        Map<String, String> courtDetails = contestedContactEmails.get(notificationRequest.getSelectedCourt());

        if (!isEmpty(notificationRequest.getSelectedCourt())) {
            expectedEmailTemplateVars.put("courtName", courtDetails.get("name"));
            expectedEmailTemplateVars.put("courtEmail", courtDetails.get("email"));
        }
        return expectedEmailTemplateVars;
    }
}