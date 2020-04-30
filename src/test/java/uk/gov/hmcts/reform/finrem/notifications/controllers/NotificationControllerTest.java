package uk.gov.hmcts.reform.finrem.notifications.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.hmcts.reform.finrem.notifications.NotificationApplication;
import uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames;
import uk.gov.hmcts.reform.finrem.notifications.domain.NotificationRequest;
import uk.gov.hmcts.reform.finrem.notifications.service.EmailService;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.hmcts.reform.finrem.notifications.NotificationConstants.AUTHORIZATION_HEADER;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.BEARER_AUTH_TOKEN;

@RunWith(SpringRunner.class)
@WebMvcTest(NotificationController.class)
@ContextConfiguration(classes = NotificationApplication.class)
public class NotificationControllerTest {

    private static final String NOTIFY_HWF_SUCCESSFUL_URL = "/notify/hwf-successful";
    private static final String NOTIFY_ASSIGN_TO_JUDGE_URL = "/notify/assign-to-judge";
    private static final String NOTIFY_CONSENT_ORDER_MADE_URL = "/notify/consent-order-made";
    private static final String NOTIFY_CONSENT_ORDER_NOT_APPROVED_URL = "/notify/consent-order-not-approved";
    private static final String NOTIFY_CONSENT_ORDER_AVAILABLE_URL = "/notify/consent-order-available";
    private static final String NOTIFY_CONTESTED_HWF_SUCCESSFUL_URL = "/notify/contested/hwf-successful";
    private static final String NOTIFY_CONTESTED_PREPARE_FOR_HEARING_URL = "/notify/contested/prepare-for-hearing";

    @MockBean
    private EmailService emailService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private String setupFile(String filename) throws Exception {
        objectMapper = new ObjectMapper();
        JsonNode request = objectMapper.readTree(new File(getClass()
            .getResource("/fixtures/" + filename + ".json").toURI()));
        return request.toString();
    }

    @Test
    public void sendEmailForHwfSuccessFul() throws Exception {
        mvc.perform(post(NOTIFY_HWF_SUCCESSFUL_URL)
            .content(setupFile("hwfSuccessfulEmail"))
            .header(AUTHORIZATION_HEADER, BEARER_AUTH_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(emailService, times(1))
                .sendConfirmationEmail(any(NotificationRequest.class), any(EmailTemplateNames.class));
    }

    @Test
    public void sendEmailForAssignedToJudge() throws Exception {
        mvc.perform(post(NOTIFY_ASSIGN_TO_JUDGE_URL)
            .content(setupFile("assignedToJudge"))
            .header(AUTHORIZATION_HEADER, BEARER_AUTH_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(emailService, times(1))
            .sendConfirmationEmail(any(NotificationRequest.class), any(EmailTemplateNames.class));
    }

    @Test
    public void sendEmailForConsentOrderMade() throws Exception {
        mvc.perform(post(NOTIFY_CONSENT_ORDER_MADE_URL)
            .content(setupFile("consentOrderMade"))
            .header(AUTHORIZATION_HEADER, BEARER_AUTH_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(emailService, times(1))
            .sendConfirmationEmail(any(NotificationRequest.class), any(EmailTemplateNames.class));
    }

    @Test
    public void sendEmailForConsentOrderNotApproved() throws Exception {
        mvc.perform(post(NOTIFY_CONSENT_ORDER_NOT_APPROVED_URL)
            .content(setupFile("consentOrderNotApproved"))
            .header(AUTHORIZATION_HEADER, BEARER_AUTH_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(emailService, times(1))
            .sendConfirmationEmail(any(NotificationRequest.class), any(EmailTemplateNames.class));
    }

    @Test
    public void sendEmailForConsentOrderAvailable() throws Exception {
        mvc.perform(post(NOTIFY_CONSENT_ORDER_AVAILABLE_URL)
            .content(setupFile("consentOrderAvailable"))
            .header(AUTHORIZATION_HEADER, BEARER_AUTH_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(emailService, times(1))
            .sendConfirmationEmail(any(NotificationRequest.class), any(EmailTemplateNames.class));
    }

    @Test
    public void sendEmailForContestedHwfSuccessful() throws Exception {
        mvc.perform(post(NOTIFY_CONTESTED_HWF_SUCCESSFUL_URL)
            .content(setupFile("hwfSuccessfulEmail"))
            .header(AUTHORIZATION_HEADER, BEARER_AUTH_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(emailService, times(1))
            .sendConfirmationEmail(any(NotificationRequest.class), any(EmailTemplateNames.class));
    }

    @Test
    public void sendEmailForPrepareForHearing() throws Exception {
        mvc.perform(post(NOTIFY_CONTESTED_PREPARE_FOR_HEARING_URL)
            .content(setupFile("prepareForHearing"))
            .header(AUTHORIZATION_HEADER, BEARER_AUTH_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(emailService, times(1))
            .sendConfirmationEmail(any(NotificationRequest.class), any(EmailTemplateNames.class));
    }

    @Test
    public void shouldNotSendEmailForPrepareForHearingWhenRequestIsEmpty() throws Exception {
        mvc.perform(post(NOTIFY_CONTESTED_PREPARE_FOR_HEARING_URL)
            .content(setupFile("emptyData"))
            .header(AUTHORIZATION_HEADER, BEARER_AUTH_TOKEN)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotSendEmailForPrepareForHearingWhenRequestIsInvalid() throws Exception {
        mvc.perform(post(NOTIFY_CONTESTED_PREPARE_FOR_HEARING_URL)
            .content((setupFile("prepareForHearingInvalid")))
            .header(AUTHORIZATION_HEADER, BEARER_AUTH_TOKEN)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest());
    }

}