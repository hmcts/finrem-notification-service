package uk.gov.hmcts.reform.finrem.notifications.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NotificationRequestTest {

    private NotificationRequest underTest;

    @Test
    public void shouldGetHwfNotificationRequestData() {
        underTest = new NotificationRequest("123456",
                "45623", "Padmaja", "test@test.com", "nottingham");
        assertEquals("123456", underTest.getCaseReferenceNumber());
        assertEquals("45623", underTest.getSolicitorReferenceNumber());
        assertEquals("Padmaja", underTest.getName());
        assertEquals("test@test.com", underTest.getNotificationEmail());
        assertEquals("nottingham", underTest.getSelectedCourt());
    }

    @Test
    public void shouldNotGetHwfNotificationRequestData() {
        underTest = new NotificationRequest();
        assertNull(underTest.getCaseReferenceNumber());
        assertNull(underTest.getSolicitorReferenceNumber());
        assertNull(underTest.getName());
        assertNull(underTest.getNotificationEmail());
        assertNull(underTest.getSelectedCourt());
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
        assertEquals("Prashanth", underTest.getName());
        assertEquals("test1@test1.com", underTest.getNotificationEmail());
    }

    @Test
    public void shouldSetAndGetHwfNotificationRequestDataForContested() {
        underTest = new NotificationRequest();
        setNotificationData();
        underTest.setSelectedCourt("nottingham");
        assertNotificationData();
        assertEquals("nottingham", underTest.getSelectedCourt());
    }

    private void setNotificationData() {
        underTest.setName("Prashanth");
        underTest.setSolicitorReferenceNumber("123123");
        underTest.setCaseReferenceNumber("case1234");
        underTest.setNotificationEmail("test1@test1.com");
    }
}