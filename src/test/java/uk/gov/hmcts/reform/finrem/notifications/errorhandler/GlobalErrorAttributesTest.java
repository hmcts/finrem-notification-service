package uk.gov.hmcts.reform.finrem.notifications.errorhandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;
import uk.gov.hmcts.reform.finrem.notifications.common.JwtFactory;
import uk.gov.hmcts.reform.finrem.notifications.domain.Jwt;
import uk.gov.hmcts.reform.finrem.notifications.exception.JwtParsingException;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Map;

import javax.xml.bind.ValidationException;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GlobalErrorAttributesTest {

    private static final String JAVAX_SERVLET_ERROR_STATUS_CODE = "javax.servlet.error.status_code";
    private static final String ERROR = ".ERROR";
    public static final String INVALID_VALUE = "Value is invalid";
    @Mock
    private RequestAttributes mockRequestAttributes;

    private final GlobalErrorAttributes underTest = new GlobalErrorAttributes();

    @Test
    public void getErrorAttributesShouldIncludeAllAttributesFromDefaultErrorAttributes() {
        given(mockRequestAttributes.getAttribute(JAVAX_SERVLET_ERROR_STATUS_CODE, 0))
                .willReturn(400);
        given(mockRequestAttributes.getAttribute(DefaultErrorAttributes.class.getName() + ERROR, 0))
                .willReturn(new ValidationException(INVALID_VALUE));

        Map<String, Object> errorAttributes =
                underTest.getErrorAttributes(mockRequestAttributes, false);

        assertNotNull(errorAttributes.get("timestamp"));
        assertEquals(400, errorAttributes.get("status"));
        assertEquals("Bad Request", errorAttributes.get("error"));
        assertEquals("javax.xml.bind.ValidationException", errorAttributes.get("exception"));
        assertEquals(INVALID_VALUE, errorAttributes.get("message"));
    }

    @Test
    public void getErrorAttributesShouldReturnErrorCodeWhenCorrectRequestAttributeIsSet() {
        given(mockRequestAttributes.getAttribute(JAVAX_SERVLET_ERROR_STATUS_CODE, 0))
                .willReturn(400);
        given(mockRequestAttributes.getAttribute("javax.servlet.error.error_code", 0))
                .willReturn("validationFailure");
        given(mockRequestAttributes.getAttribute(DefaultErrorAttributes.class.getName() + ERROR, 0))
                .willReturn(new ValidationException(INVALID_VALUE));

        Map<String, Object> errorAttributes =
                underTest.getErrorAttributes(mockRequestAttributes, false);

        assertEquals("validationFailure", errorAttributes.get("errorCode"));

    }

    @Test
    public void getErrorAttributesShouldNotReturnErrorCodeWhenTheErrorCodeRequestAttributeIsMissing() {
        given(mockRequestAttributes.getAttribute(JAVAX_SERVLET_ERROR_STATUS_CODE, 0))
                .willReturn(400);
        given(mockRequestAttributes.getAttribute(DefaultErrorAttributes.class.getName() + ERROR, 0))
                .willReturn(new ValidationException(INVALID_VALUE));

        Map<String, Object> errorAttributes =
            underTest.getErrorAttributes(mockRequestAttributes, false);

        assertNull(errorAttributes.get("errorCode"));

    }

    public static class JwtFactoryTest {
        private final JwtFactory jwtFactory = new JwtFactory();

        @Test
        public void shouldReturnJwtFromAuthorizationHeader() {
            String encodedJwt = "Bearer eyJhbGciOiJIUzI1NiJ9."
                + "eyJqdGkiOiI4cjF0M3Z1M2c5ZGR0OG9saGpyMmoyc2o3ZyIsInN1YiI6IjYwIiwia"
                + "WF0IjoxNTA1ODk3Mjk3LCJleHAiOjE1MDU5MjYwOTcsImRhdGEiOiJjaXRpemVuLGRpdm9yY2UtcHJpdmF0ZS1iZXRhLG"
                + "NpdGl6ZW4tbG9hMSxkaXZvcmNlLXByaXZhdGUtYmV0YS1sb2ExIiwidHlwZSI6IkFDQ0VTUyIsImlkIjoiNjAiLCJmb3J"
                + "lbmFtZSI6ImpvaG4iLCJzdXJuYW1lIjoic21pdGgiLCJkZWZhdWx0LXNlcnZpY2UiOiJEaXZvcmNlIiwibG9hIjoxLCJk"
                + "ZWZhdWx0LXVybCI6Imh0dHBzOi8vd3d3LWxvY2FsLnJlZ2lzdHJhdGlvbi50ZXN0VXJsLmNvbTo5MDAwL3BvYy9kaXZvc"
                + "mNlIiwiZ3JvdXAiOiJkaXZvcmNlLXByaXZhdGUtYmV0YSJ9.E4TxUfBKZg6bUvlsDUonWyIMEEoRuzyGUneFiW8iEo0";

            Jwt jwt = jwtFactory.create(encodedJwt);

            assertEquals("https://www-local.registration.testUrl.com:9000/poc/divorce", jwt.getDefaultUrl());
            assertEquals("60", jwt.getSubject());
            assertEquals("citizen,divorce-private-beta,citizen-loa1,divorce-private-beta-loa1", jwt.getData());
            assertEquals("ACCESS", jwt.getType());
            assertEquals("john", jwt.getForename());
            assertEquals("smith", jwt.getSurname());
            assertEquals(60, jwt.getId());
            assertEquals(LocalDate.of(2017, 9, 20), jwt.getExpiration());
            assertEquals(LocalDate.of(2017, 9, 20), jwt.getIssuedAt());
            assertEquals("8r1t3vu3g9ddt8olhjr2j2sj7g", jwt.getJwtId());
            assertEquals("Divorce", jwt.getDefaultService());
            assertEquals(1, jwt.getLevelOfAssurance());
            assertEquals("divorce-private-beta", jwt.getGroup());
        }

        @Test
        public void createThrowsJwtParsingException() {
            String encodedJwt = "abc";
            try {
                jwtFactory.create(encodedJwt);
                fail("Expected JwtParsingException");
            } catch (JwtParsingException e) {
                assertTrue(e.getCause() instanceof ParseException);
            }
        }

    }
}
