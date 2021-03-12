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
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_ASSIGNED_TO_JUDGE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENTED_GENERAL_ORDER;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_AVAILABLE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_AVAILABLE_CTSC;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_HEARING_REQUEST;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_MADE;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_NOT_APPROVED;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_CONSENT_ORDER_NOT_APPROVED_SENT;
import static uk.gov.hmcts.reform.finrem.notifications.domain.EmailTemplateNames.FR_HWF_SUCCESSFUL;

@RestController
@RequestMapping(path = "/notify")
@Slf4j
@Validated
public class ConsentedNotificationController {

    @Autowired
    private EmailService emailService;

    @PostMapping(path = "/hwf-successful", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for HWF Successful.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "HWFSuccessful e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailHwfSuccessFul(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification that the HWF is successful and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for HWFSuccessful. Case ID : {}",
                 notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_HWF_SUCCESSFUL);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/assign-to-judge", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for a case assigned to judge.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Assigned to Judge e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailAssignToJudge(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification that a case is assigned to judge and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for Case assigned to Judge Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_ASSIGNED_TO_JUDGE);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/consent-order-made", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Consent order Made.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Consent order made e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailConsentOrderApproved(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification that a consent order is made and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for consent order made. Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_MADE);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/consent-order-not-approved", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Consent order Not Approved.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Consent order not approved e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailConsentOrderNotApproved(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification that a consent order is made and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for consent order not approved, Case ID : {}",
                notificationRequest.getCaseReferenceNumber());

        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_NOT_APPROVED);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/consent-order-not-approved-sent", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Consent order Not Approved Sent.")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Consent order not approved sent e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailConsentOrderNotApprovedSent(
        @RequestBody
        @ApiParam(value = "The fixtures contains case reference number,"
            + " solicitorReferenceNumber and the email address that will receive"
            + " the notification that a consent order is made and all are mandatory")
        final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for consent order not approved sent, Case ID : {}",
                notificationRequest.getCaseReferenceNumber());

        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_NOT_APPROVED_SENT);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/consent-order-available", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Consent order available.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Consent order available e-mail notification sent successfully")})
    public ResponseEntity<Void> sendEmailConsentOrderAvailable(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification that a consent order is made and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for consent order available Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_AVAILABLE);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/consent-order-available-ctsc", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail to CTSC for Consent order available.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "CTSC Consent order available e-mail notification sent successfully")})
    public ResponseEntity<Void> sendEmailConsentOrderSentCtsc(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification to CTSC that a consent order is made and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for CTSC consent order available Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_AVAILABLE_CTSC);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping(path = "/consent-order-hearing-request", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Consent order hearing request.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Consent order hearing request e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailConsentOrderHearingRequest(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " divorce case number and the email address that will receive"
                    + " the notification and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for Consent order hearing request, Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENT_ORDER_HEARING_REQUEST);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(path = "/general-order", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "send e-mail for Consented general order.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Consented general order e-mail sent successfully")})
    public ResponseEntity<Void> sendEmailConsentedGeneralOrder(
            @RequestBody
            @ApiParam(value = "The fixtures contains case reference number,"
                    + " solicitorReferenceNumber and the email address that will receive"
                    + " the notification and all are mandatory")
            final NotificationRequest notificationRequest) {
        log.info("Received request for notification email for Consented general order, Case ID : {}",
                notificationRequest.getCaseReferenceNumber());
        emailService.sendConfirmationEmail(notificationRequest, FR_CONSENTED_GENERAL_ORDER);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
