package uk.gov.hmcts.reform.finrem.notifications.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.finrem.notifications.domain.NotificationRequest;
import uk.gov.hmcts.reform.finrem.notifications.service.EmailService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_BARRISTER_ACCESS_ADDED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_BARRISTER_ACCESS_REMOVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_APPLICATION_ISSUED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_CONSENT_ORDER_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_CONSENT_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_DRAFT_ORDER;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_APPLICATION_OUTCOME;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_EMAIL;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_ORDER;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_GENERAL_ORDER_CONSENT;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_HWF_SUCCESSFUL;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_INTERIM_HEARING;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_NOC_CASEWORKER;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_NOTICE_OF_CHANGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_PREPARE_FOR_HEARING;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_UPDATE_FRC_COURT;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTESTED_UPDATE_FRC_SOL;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTEST_ORDER_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONTEST_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_REJECT_GENERAL_APPLICATION;


@RestController
@RequestMapping(path = "/notify/contested")
@Slf4j
@Validated
public class ContestedNotificationController {

    @Autowired
    private EmailService emailService;

    @PostMapping(path = "/hwf-successful", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Contested Case HWF Successful.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Contested Case HWFSuccessful e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailHwfSuccessFul(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification that the HWF is successful and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for HWFSuccessful. Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_HWF_SUCCESSFUL);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/application-issued", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Application Issued.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Application Issued e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailApplicationIssued(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification that the application has been issued and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for Contested 'Application Issued'. Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_APPLICATION_ISSUED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/order-approved", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for 'Contest Order Approved'.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Application Issued e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailContestOrderApproved(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification that the contest order has been approved and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for 'Contest Order Approved'. Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTEST_ORDER_APPROVED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/prepare-for-hearing", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for 'Prepare for hearing'.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Prepare for hearing e-mail sent successfully")})
    public ResponseEntity<Void> sendContestedEmailPrepareForHearing(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification that the case is in the 'Prepare for a hearing' state")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for 'Prepare for hearing'. Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_PREPARE_FOR_HEARING);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/draft-order", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Solicitor to Draft Order")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Draft Order e-mail sent successfully")})
    public ResponseEntity<Void> sendContestedDraftOrder(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for Contested 'Draft Order'. Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_DRAFT_ORDER);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/order-not-approved", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Contest order Not Approved.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Contest order not approved e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailContestOrderNotApproved(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for contest order not approved, Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTEST_ORDER_NOT_APPROVED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/consent-order-approved", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Consent order Approved.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Consent Order Approved e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailContestedConsentOrderApproved(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification that a contest order is made and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for consent order approved, Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_CONSENT_ORDER_APPROVED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/consent-order-not-approved", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Consent Order Not Approved.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Consent order approved e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailContestedConsentOrderNotApproved(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification that a Contest Order is not approved and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for Consent Order Not Approved, Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_CONSENT_ORDER_NOT_APPROVED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/consent-general-order", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Contested general order (consent).")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Contested general order (consent) e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailContestedGeneralOrderConsent(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for Contested general order (consent), Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_GENERAL_ORDER_CONSENT);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/general-order", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Contested general order.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Contested general order e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailContestedGeneralOrder(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for Contested general order, Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_GENERAL_ORDER);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/general-application-refer-to-judge", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Contested general application refer to judge.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Contested general application refer to judge e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailContestedGeneralApplicationReferToJudge(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for Contested general application refer to judge, Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_GENERAL_APPLICATION_REFER_TO_JUDGE);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/general-application-outcome", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Contested General Application Outcome")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Contested General Application Outcome e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailContestedGeneralApplicationOutcome(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for Contested General Application Outcome, Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_GENERAL_APPLICATION_OUTCOME);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/general-email", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send a general email")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "General e-mail notification sent successfully")})
    public ResponseEntity<Void> sendGeneralEmail(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " generalEmailBody and the email address that will receive"
                    + " the notification that a general email is sent and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for contested general email Notification request : {}",
                notificationRequest);
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_GENERAL_EMAIL);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/prepare-for-interim-hearing-sent", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for 'Prepare for interim hearing'")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Prepare for interim hearing sent e-mail sent successfully")})
    public ResponseEntity<Void> sendContestedEmailPrepareForInterimHearingSent(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for 'Prepare for interim hearing sent'. Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_INTERIM_HEARING);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/update-frc-information", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send email for Update FRC Information event")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Update FRC Information e-mail sent successfully")})
    public ResponseEntity<Void> sendContestedEmailUpdateFrcInfo(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for 'Update FRC Information event'");
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_UPDATE_FRC_SOL);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/update-frc-information/court", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send email for Update FRC Information event to court")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Update FRC Information court e-mail sent successfully")})
    public ResponseEntity<Void> sendCourtContestedEmailUpdateFrcDetails(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email to court for 'Update FRC Information event'");
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_UPDATE_FRC_COURT);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/notice-of-change", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail to Solicitors whom have been granted access to the case")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Notice of Change sent e-mail sent successfully")})
    public ResponseEntity<Void> sendContestedEmailNoticeOfChange(
        @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for 'Notice of Change'. Case ID : {}",
            notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_NOTICE_OF_CHANGE);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/notice-of-change/caseworker", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail to Solicitors whom have been granted access to the case")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Notice of Change sent e-mail sent successfully")})
    public ResponseEntity<Void> sendContestedEmailNoticeOfChangeCaseworker(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for caseworker-invoked 'Notice of Change'. Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONTESTED_NOC_CASEWORKER);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/general-application-rejected", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail to Solicitors for General Application Rejected Event")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "General Application Rejected e-mail sent successfully")})
    public ResponseEntity<Void> sendContestedEmailRejectGeneralApplication(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for General Application Rejected event. Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_REJECT_GENERAL_APPLICATION);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/barrister-access-added", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail to Barristers for Barrister Access Added Event")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Barrister Access Added e-mail sent successfully")})
    public ResponseEntity<Void> sendContestedEmailBarristerAccessAdded(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for Barrister Access Added event. Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_BARRISTER_ACCESS_ADDED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/barrister-access-removed", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail to Barristers for Barrister Access Removed Event")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Barrister Access Removed e-mail sent successfully")})
    public ResponseEntity<Void> sendContestedEmailBarristerAccessRemoved(
            @RequestBody final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for Barrister Access Removed event. Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_BARRISTER_ACCESS_REMOVED);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
