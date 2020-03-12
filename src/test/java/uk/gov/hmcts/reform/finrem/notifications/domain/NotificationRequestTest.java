package uk.gov.hmcts.reform.finrem.notifications.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_CASE_ID;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_COURT;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_EMAIL;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_NAME;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_REFERENCE;

public class NotificationRequestTest {

    private NotificationRequest notificationRequest;

    @Before
    public void setup() {
        notificationRequest = new NotificationRequest();

        notificationRequest.setCaseReferenceNumber(TEST_CASE_ID);
        notificationRequest.setSolicitorReferenceNumber(TEST_SOLICITOR_REFERENCE);
        notificationRequest.setName(TEST_SOLICITOR_NAME);
        notificationRequest.setNotificationEmail(TEST_SOLICITOR_EMAIL);
    }

    @Test
    public void shouldSetAndGetHwfNotificationRequestData() {

        assertEquals(TEST_CASE_ID, notificationRequest.getCaseReferenceNumber());
        assertEquals(TEST_SOLICITOR_REFERENCE, notificationRequest.getSolicitorReferenceNumber());
        assertEquals(TEST_SOLICITOR_NAME, notificationRequest.getName());
        assertEquals(TEST_SOLICITOR_EMAIL, notificationRequest.getNotificationEmail());
    }

    @Test
    public void shouldSetAndGetHwfNotificationRequestDataForContested() {
        notificationRequest.setSelectedCourt(TEST_COURT);

        assertEquals(TEST_CASE_ID, notificationRequest.getCaseReferenceNumber());
        assertEquals(TEST_SOLICITOR_REFERENCE, notificationRequest.getSolicitorReferenceNumber());
        assertEquals(TEST_SOLICITOR_NAME, notificationRequest.getName());
        assertEquals(TEST_SOLICITOR_EMAIL, notificationRequest.getNotificationEmail());
        assertEquals(TEST_COURT, notificationRequest.getSelectedCourt());
    }
}