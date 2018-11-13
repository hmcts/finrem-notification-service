package uk.gov.hmcts.reform.finrem.notifications.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.finrem.notifications.client.EmailClient;
import uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames;
import uk.gov.hmcts.reform.finrem.notifications.domain.EmailToSend;
import uk.gov.hmcts.reform.finrem.notifications.domain.HwfSuccessfulNotificationRequest;
import uk.gov.service.notify.NotificationClientException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private EmailClient emailClient;

    @Value("#{${uk.gov.notify.email.templates}}")
    private Map<String, String> emailTemplates;

    @Value("#{${uk.gov.notify.email.template.vars}}")
    private Map<String, Map<String, String>> emailTemplateVars;

    public void sendHwfSuccessfulConfirmationEmail(HwfSuccessfulNotificationRequest notificationRequest) {
        String templateName = EmailTemplateNames.FR_HWF_SUCCESSFUL.name();
        Map<String, String> templateVars = new HashMap<>();
        templateVars.put("caseReferenceNumber",notificationRequest.getCaseReferenceNumber());
        templateVars.put("solicitorReferenceNumber", notificationRequest.getSolicitorReferenceNumber());
        templateVars.put("name", notificationRequest.getName());
        templateVars.putAll(emailTemplateVars.get(templateName));
        EmailToSend emailToSend = generateEmail(notificationRequest.getNotificationEmail(), templateName, templateVars);
        sendEmail(emailToSend, "send HWF Successful Confirmation email");
    }

    private EmailToSend generateEmail(String destinationAddress,
                                      String templateName,
                                      Map<String, String> templateVars) {
        String referenceId = UUID.randomUUID().toString();
        String templateId = emailTemplates.get(templateName);
        return new EmailToSend(destinationAddress, templateId, templateVars, referenceId);
    }

    private void sendEmail(EmailToSend emailToSend, String emailDescription) {
        try {
            log.debug("Attempting to send {} email. Reference ID: {}", emailDescription, emailToSend.getReferenceId());
            emailClient.sendEmail(
                    emailToSend.getTemplateId(),
                    emailToSend.getEmailAddress(),
                    emailToSend.getTemplateFields(),
                    emailToSend.getReferenceId()
            );
            log.info("Sending email success. Reference ID: {}", emailToSend.getReferenceId());
        } catch (NotificationClientException e) {
            log.warn("Failed to send email. Reference ID: {}. Reason:", emailToSend.getReferenceId(), e);
        }
    }
}
