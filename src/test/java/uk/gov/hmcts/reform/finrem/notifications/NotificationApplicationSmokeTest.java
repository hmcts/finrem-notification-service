package uk.gov.hmcts.reform.finrem.notifications;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.finrem.notifications.category.SmokeTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@Category(SmokeTest.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotificationApplicationSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ApplicationContext applicationArguments;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() {
        applicationArguments.getStartupDate();
    }

    @Test
    public void shouldInvokeHealthEndPoint() {
        ResponseEntity<String> forEntity = this.testRestTemplate
                .getForEntity("http://localhost:" + port + "/health", String.class);
        assertThat(forEntity.getStatusCode().value(), is(200));
    }
}
