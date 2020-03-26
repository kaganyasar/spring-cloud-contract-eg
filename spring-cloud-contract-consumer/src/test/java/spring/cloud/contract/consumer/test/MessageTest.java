package spring.cloud.contract.consumer.test;

import data.Person;
import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerRule;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import spring.cloud.contract.consumer.service.ConsumerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsumerApplication.class)
public class MessageTest {

    @Rule
    public StubRunnerRule stubRunnerRule = new StubRunnerRule()
            .downloadStub("org.example", "spring-cloud-contract-producer", "1.0-SNAPSHOT", "stubs")
            .withPort(8083)
            .stubsMode(StubRunnerProperties.StubsMode.LOCAL);

    @Test
    public void get_person_from_service_contract() {
        // given:
        RestTemplate restTemplate = new RestTemplate();

        // when:
        ResponseEntity<Person> personResponseEntity = restTemplate.getForEntity("http://localhost:8083/person/4", Person.class);

        // then:
        BDDAssertions.then(personResponseEntity.getStatusCodeValue()).isEqualTo(200);
        BDDAssertions.then(personResponseEntity.getBody().getId()).isEqualTo(4L);
        BDDAssertions.then(personResponseEntity.getBody().getName()).isEqualTo("Jack");
        BDDAssertions.then(personResponseEntity.getBody().getSurname()).isEqualTo("Black");

    }
}