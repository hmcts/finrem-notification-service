package uk.gov.hmcts.reform.finrem.notifications.exception;

public class NotificationException extends RuntimeException {

    public NotificationException(String message) {
        super(message);
    }

    public NotificationException(Exception cause) {
        super(cause);
    }
}
