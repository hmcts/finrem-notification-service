package uk.gov.hmcts.reform.finrem.notifications.entityfactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DefaultHttpEntityFactoryTest {

    @InjectMocks
    private DefaultHttpEntityFactory httpEntityFactory;

    @Test
    public void shouldReturnEntityWithJsonAcceptHeadersWhenCallingCreateEntityForHealthCheck() {
        HttpEntity<Object> httpEntity = httpEntityFactory.createRequestEntityForHealthCheck();

        assertThat(httpEntity.getHeaders().size()).isEqualTo(1);
        assertThat(httpEntity.getHeaders().getAccept().get(0)).isEqualTo(MediaType.APPLICATION_JSON_UTF8);
        assertThat(httpEntity.getBody()).isNull();
    }

}