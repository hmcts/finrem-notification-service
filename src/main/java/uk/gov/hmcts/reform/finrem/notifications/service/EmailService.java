package uk.gov.hmcts.reform.finrem.notifications.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.finrem.notifications.client.EmailClient;
import uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames;
import uk.gov.hmcts.reform.finrem.notifications.domain.EmailToSend;
import uk.gov.hmcts.reform.finrem.notifications.domain.NotificationRequest;
import uk.gov.service.notify.NotificationClientException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.util.StringUtils.isEmpty;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private EmailClient emailClient;

    @Value("#{${uk.gov.notify.email.templates}}")
    private Map<String, String> emailTemplates;

    @Value("#{${uk.gov.notify.email.template.vars}}")
    private Map<String, Map<String, String>> emailTemplateVars;

    @Value("#{${uk.gov.notify.email.contestedContactEmails}}")
    private Map<String, Map<String, String>> contestedContactEmails;

    private static final String CONTESTED = "contested";

    public void sendConfirmationEmail(NotificationRequest notificationRequest, EmailTemplateNames template) {
        Map<String, String> templateVars = buildTemplateVars(notificationRequest, template.name());
        EmailToSend emailToSend = generateEmail(notificationRequest.getNotificationEmail(), template.name(),
                templateVars);
        sendEmail(emailToSend, "send Confirmation email for " + template.name());
    }

    protected Map<String, String> buildTemplateVars(NotificationRequest notificationRequest, String templateName) {
        Map<String, String> templateVars = new HashMap<>();

        templateVars.put("caseReferenceNumber", notificationRequest.getCaseReferenceNumber());
        templateVars.put("solicitorReferenceNumber", notificationRequest.getSolicitorReferenceNumber());
        templateVars.put("divorceCaseNumber", notificationRequest.getDivorceCaseNumber());
        templateVars.put("notificationEmail", notificationRequest.getNotificationEmail());
        templateVars.put("name", notificationRequest.getName());

        //contested emails notifications require the court information, consented does not
        if (CONTESTED.equals(notificationRequest.getCaseType()) && !isEmpty(notificationRequest.getSelectedCourt())) {
            Map<String, String> courtDetails = contestedContactEmails.get(notificationRequest.getSelectedCourt());

            templateVars.put("courtName", courtDetails.get("name"));
            templateVars.put("courtEmail", courtDetails.get("email"));
        }

        templateVars.putAll(emailTemplateVars.get(templateName));
        return templateVars;
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
            log.info("Attempting to send {} email. Reference ID: {}", emailDescription, emailToSend.getReferenceId());
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
