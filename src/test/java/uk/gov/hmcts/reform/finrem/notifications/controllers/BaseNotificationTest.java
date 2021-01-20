package uk.gov.hmcts.reform.finrem.notifications.controllers;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames;
import uk.gov.hmcts.reform.finrem.notifications.domain.NotificationRequest;
import uk.gov.hmcts.reform.finrem.notifications.service.EmailService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.hmcts.reform.finrem.notifications.NotificationConstants.AUTHORIZATION_HEADER;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.BEARER_AUTH_TOKEN;

public class BaseNotificationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private EmailService emailService;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected void performPostRequestWithMockContent(String postUri, EmailTemplateNames template) throws Exception {
        mvc.perform(post(postUri)
            .content("{\"any\": \"content\"}")
            .header(AUTHORIZATION_HEADER, BEARER_AUTH_TOKEN)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(emailService).sendConfirmationEmail(any(NotificationRequest.class), eq(template));
    }
}
