package uk.gov.hmcts.reform.finrem.notification.smoketests;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SmokeTestConfiguration.class})
public class NotificationSmokeTests {

    @Value("${url}")
    private String url;

    @Value("${http.timeout}")
    private int connectionTimeOut;

    @Value("${http.requestTimeout}")
    private int socketTimeOut;

    @Value("${http.readTimeout}")
    private int connectionManagerTimeOut;

    private RestAssuredConfig config;

    @BeforeEach
    public void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
        config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", connectionTimeOut)
                        .setParam("http.socket.timeout", socketTimeOut)
                        .setParam("http.connection-manager.timeout", connectionManagerTimeOut));
    }

    @Test
    public void shouldGetOkStatusFromHealthEndpoint() {
        given().config(config)
                .when()
                .get(url + "/health")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
