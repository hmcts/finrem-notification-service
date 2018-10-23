package uk.gov.hmcts.reform.finrem.notifications.exception;

public class JwtParsingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public JwtParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
