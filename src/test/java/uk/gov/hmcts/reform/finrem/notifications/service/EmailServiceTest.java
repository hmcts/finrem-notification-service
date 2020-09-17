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

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.CONSENTED;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.CONTESTED;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_CASE_FAMILY_MAN_ID;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_DIVORCE_CASE_NUMBER;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_EMAIL;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_NAME;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_REFERENCE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_ASSIGNED_TO_JUDGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_AVAILABLE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_MADE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_HWF_SUCCESSFUL;
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

    private NotificationRequest notificationRequest;

    @Before
    public void setUp() {
        notificationRequest = new NotificationRequest();
        notificationRequest.setNotificationEmail(TEST_SOLICITOR_EMAIL);
        notificationRequest.setCaseReferenceNumber(TEST_CASE_FAMILY_MAN_ID);
        notificationRequest.setSolicitorReferenceNumber(TEST_SOLICITOR_REFERENCE);
        notificationRequest.setDivorceCaseNumber(TEST_DIVORCE_CASE_NUMBER);
        notificationRequest.setName(TEST_SOLICITOR_NAME);
    }

    private void setConsentedData() {
        notificationRequest.setCaseType(CONSENTED);
    }

    private void setContestedData() {
        notificationRequest.setCaseType(CONTESTED);
        notificationRequest.setSelectedCourt("nottingham");
    }

    @Test
    public void sendHwfSuccessfulConfirmationEmailConsented() throws NotificationClientException {
        setConsentedData();

        Map<String, String> returnedTemplateVars =
                emailService.buildTemplateVars(notificationRequest, FR_HWF_SUCCESSFUL.name());

        assertNull(returnedTemplateVars.get("courtName"));
        assertNull(returnedTemplateVars.get("courtEmail"));

        returnedTemplateVars.putAll(emailTemplateVars.get(FR_HWF_SUCCESSFUL.name()));
        emailService.sendConfirmationEmail(notificationRequest, FR_HWF_SUCCESSFUL);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_HWF_SUCCESSFUL.name())),
                eq(TEST_SOLICITOR_EMAIL),
                eq(returnedTemplateVars),
                anyString());
    }

    @Test
    public void sendHwfSuccessfulConfirmationEmailContested() throws NotificationClientException {
        setContestedData();

        Map<String, String> returnedTemplateVars =
                emailService.buildTemplateVars(notificationRequest, FR_CONTESTED_HWF_SUCCESSFUL.name());

        assertEquals(returnedTemplateVars.get("courtName"), "Nottingham FRC");
        assertEquals(returnedTemplateVars.get("courtEmail"), "FRCNottingham@justice.gov.uk");
        returnedTemplateVars.putAll(emailTemplateVars.get(FR_CONTESTED_HWF_SUCCESSFUL.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_HWF_SUCCESSFUL);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_CONTESTED_HWF_SUCCESSFUL.name())),
                eq(TEST_SOLICITOR_EMAIL),
                eq(returnedTemplateVars),
                anyString());
    }

    @Test
    public void sendHwfSuccessfulConfirmationEmailShouldNotPropagateNotificationClientException()
            throws NotificationClientException {
        setConsentedData();
        doThrow(new NotificationClientException(new Exception("Exception inception")))
                .when(mockClient).sendEmail(anyString(), anyString(), eq(null), anyString());

        try {
            emailService.sendConfirmationEmail(notificationRequest, FR_HWF_SUCCESSFUL);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void sendAssignedToJudgeConfirmationEmailConsented() throws NotificationClientException {
        setConsentedData();

        Map<String, String> returnedTemplateVars =
                emailService.buildTemplateVars(notificationRequest, FR_ASSIGNED_TO_JUDGE.name());

        assertNull(returnedTemplateVars.get("courtName"));
        assertNull(returnedTemplateVars.get("courtEmail"));
        returnedTemplateVars.putAll(emailTemplateVars.get(FR_ASSIGNED_TO_JUDGE.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_ASSIGNED_TO_JUDGE);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_ASSIGNED_TO_JUDGE.name())),
                eq(TEST_SOLICITOR_EMAIL),
                eq(returnedTemplateVars),
                anyString());
    }

    @Test
    public void sendAssignedToJudgeConfirmationEmailShouldNotPropagateNotificationClientException()
            throws NotificationClientException {
        setConsentedData();
        doThrow(new NotificationClientException(new Exception("Exception inception")))
                .when(mockClient).sendEmail(anyString(), anyString(), eq(null), anyString());
        try {
            emailService.sendConfirmationEmail(notificationRequest, FR_ASSIGNED_TO_JUDGE);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void sendConsentOrderMadeConfirmationEmail() throws NotificationClientException {
        setConsentedData();

        Map<String, String> returnedTemplateVars =
                emailService.buildTemplateVars(notificationRequest, FR_CONSENT_ORDER_MADE.name());

        assertNull(returnedTemplateVars.get("courtName"));
        assertNull(returnedTemplateVars.get("courtEmail"));
        returnedTemplateVars.putAll(emailTemplateVars.get(FR_CONSENT_ORDER_MADE.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_MADE);
        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_CONSENT_ORDER_MADE.name())),
                eq(TEST_SOLICITOR_EMAIL),
                eq(returnedTemplateVars),
                anyString());
    }

    @Test
    public void sendConsentOrderMadeConfirmationEmailShouldNotPropagateNotificationClientException()
            throws NotificationClientException {
        setConsentedData();
        doThrow(new NotificationClientException(new Exception("Exception inception")))
                .when(mockClient).sendEmail(anyString(), anyString(), eq(null), anyString());
        try {
            emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_MADE);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void sendConsentOrderNotApprovedEmail() throws NotificationClientException {
        setConsentedData();

        Map<String, String> returnedTemplateVars =
                emailService.buildTemplateVars(notificationRequest, FR_CONSENT_ORDER_NOT_APPROVED.name());

        assertNull(returnedTemplateVars.get("courtName"));
        assertNull(returnedTemplateVars.get("courtEmail"));
        returnedTemplateVars.putAll(emailTemplateVars.get(FR_CONSENT_ORDER_NOT_APPROVED.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_NOT_APPROVED);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_CONSENT_ORDER_NOT_APPROVED.name())),
                eq(TEST_SOLICITOR_EMAIL),
                eq(returnedTemplateVars),
                anyString());
    }

    @Test
    public void sendConsentOrderNotApprovedEmailShouldNotPropagateNotificationClientException()
            throws NotificationClientException {
        setConsentedData();
        doThrow(new NotificationClientException(new Exception("Exception inception")))
                .when(mockClient).sendEmail(anyString(), anyString(), eq(null), anyString());
        try {
            emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_NOT_APPROVED);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void sendConsentOrderAvailableEmail() throws NotificationClientException {
        setConsentedData();

        Map<String, String> returnedTemplateVars =
                emailService.buildTemplateVars(notificationRequest, FR_CONSENT_ORDER_AVAILABLE.name());

        returnedTemplateVars.putAll(emailTemplateVars.get(FR_CONSENT_ORDER_AVAILABLE.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_AVAILABLE);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_CONSENT_ORDER_AVAILABLE.name())),
                eq(TEST_SOLICITOR_EMAIL),
                eq(returnedTemplateVars),
                anyString());
    }

    @Test
    public void sendContestedGeneralApplicationReferToJudgeEmail() throws NotificationClientException {
        setContestedData();

        Map<String, String> returnedTemplateVars =
                emailService.buildTemplateVars(notificationRequest, FR_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE.name());

        returnedTemplateVars.putAll(emailTemplateVars.get(FR_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE.name()));

        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE);

        verify(mockClient).sendEmail(
                eq(emailTemplates.get(FR_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE.name())),
                eq(TEST_SOLICITOR_EMAIL),
                eq(returnedTemplateVars),
                anyString());
    }

    @Test
    public void sendConsentOrderAvailableEmailShouldNotPropagateNotificationClientException()
            throws NotificationClientException {
        setConsentedData();
        doThrow(new NotificationClientException(new Exception("Exception inception")))
                .when(mockClient).sendEmail(anyString(), anyString(), eq(null), anyString());
        try {
            emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_AVAILABLE);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void shouldBuildTemplateVarsForContested() {
        setContestedData();

        Map<String, String> returnedTemplateVars =
                emailService.buildTemplateVars(notificationRequest,FR_CONTESTED_HWF_SUCCESSFUL.name());

        assertEquals(returnedTemplateVars.get("courtName"), "Nottingham FRC");
        assertEquals(returnedTemplateVars.get("courtEmail"), "FRCNottingham@justice.gov.uk");
    }

    @Test
    public void shouldBuildTemplateVarsForConsented() {
        setConsentedData();

        Map<String, String> returnedTemplateVars =
                emailService.buildTemplateVars(notificationRequest, FR_HWF_SUCCESSFUL.name());

        assertNull(returnedTemplateVars.get("courtName"));
        assertNull(returnedTemplateVars.get("courtEmail"));
    }
}