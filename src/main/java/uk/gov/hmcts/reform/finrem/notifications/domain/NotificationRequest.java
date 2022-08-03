package uk.gov.hmcts.reform.finrem.notifications.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationRequest {
    @NotNull
    private String caseReferenceNumber;
    @NotNull
    private String solicitorReferenceNumber;
    @NotNull
    private String divorceCaseNumber;
    @NotNull
    private String name;
    @Email
    private String notificationEmail;
    private String selectedCourt;
    private String caseType;
    private String generalEmailBody;
    private String phoneOpeningHours;
    private String caseOrderType;
    private String camelCaseOrderType;
}
