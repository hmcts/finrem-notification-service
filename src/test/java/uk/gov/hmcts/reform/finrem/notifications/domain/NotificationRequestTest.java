package uk.gov.hmcts.reform.finrem.notifications.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.CONTESTED;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_CASE_REFERENCE_NUMBER;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_COURT_EMAIL;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_COURT_NAME;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_DIVORCE_CASE_NUMBER;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_EMAIL;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_NAME;
import static uk.gov.hmcts.reform.finrem.notifications.TestConstants.TEST_SOLICITOR_REFERENCE;

public class NotificationRequestTest {

    private NotificationRequest underTest;

    @Test
    public void shouldGetHwfNotificationRequestData() {
        underTest = new NotificationRequest(TEST_CASE_REFERENCE_NUMBER, TEST_SOLICITOR_REFERENCE, TEST_DIVORCE_CASE_NUMBER,
                TEST_SOLICITOR_NAME, TEST_SOLICITOR_EMAIL, TEST_COURT_NAME, TEST_COURT_EMAIL, CONTESTED);
        assertEquals(TEST_CASE_REFERENCE_NUMBER, underTest.getCaseReferenceNumber());
        assertEquals(TEST_SOLICITOR_REFERENCE, underTest.getSolicitorReferenceNumber());
        assertEquals(TEST_DIVORCE_CASE_NUMBER, underTest.getDivorceCaseNumber());
        assertEquals(TEST_SOLICITOR_NAME, underTest.getName());
        assertEquals(TEST_SOLICITOR_EMAIL, underTest.getNotificationEmail());
        assertEquals(TEST_COURT_NAME, underTest.getSelectedCourtName());
        assertEquals(TEST_COURT_EMAIL, underTest.getSelectedCourtEmail());
    }

    @Test
    public void shouldNotGetHwfNotificationRequestData() {
        underTest = new NotificationRequest();
        assertNull(underTest.getCaseReferenceNumber());
        assertNull(underTest.getSolicitorReferenceNumber());
        assertNull(underTest.getDivorceCaseNumber());
        assertNull(underTest.getName());
        assertNull(underTest.getNotificationEmail());
        assertNull(underTest.getSelectedCourtName());
        assertNull(underTest.getSelectedCourtEmail());
    }

    @Test
    public void shouldSetAndGetHwfNotificationRequestData() {
        underTest = new NotificationRequest();
        setNotificationData();
        assertNotificationData();
    }

    private void assertNotificationData() {
        assertEquals("case1234", underTest.getCaseReferenceNumber());
        assertEquals("123123", underTest.getSolicitorReferenceNumber());
        assertEquals("D456", underTest.getDivorceCaseNumber());
        assertEquals("Prashanth", underTest.getName());
        assertEquals("test1@test1.com", underTest.getNotificationEmail());
    }

    @Test
    public void shouldSetAndGetHwfNotificationRequestDataForContested() {
        underTest = new NotificationRequest();
        setNotificationData();
        underTest.setSelectedCourtName(TEST_COURT_NAME);
        underTest.setSelectedCourtEmail(TEST_COURT_EMAIL);
        assertNotificationData();
        assertEquals(TEST_COURT_NAME, underTest.getSelectedCourtName());
        assertEquals(TEST_COURT_EMAIL, underTest.getSelectedCourtEmail());
    }

    private void setNotificationData() {
        underTest.setName("Prashanth");
        underTest.setSolicitorReferenceNumber("123123");
        underTest.setDivorceCaseNumber("D456");
        underTest.setCaseReferenceNumber("case1234");
        underTest.setNotificationEmail("test1@test1.com");
    }
}