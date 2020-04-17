package spring.cloud.contract.consumer.test;

import data.Person;
import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerRule;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import spring.cloud.contract.consumer.service.ConsumerApplication;
import org.awaitility.Awaitility;
import spring.cloud.contract.consumer.service.stream.PersonStreamListener;

@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "org.example:spring-cloud-contract-producer:+:stubs:8080")
@SpringBootTest(classes = ConsumerApplication.class)
public class MessageTest {

    @Autowired
    StubTrigger trigger;

    @Autowired
    PersonStreamListener application;


    @Test
    public void get_person_from_service_contract() {
        // given:
        RestTemplate restTemplate = new RestTemplate();

        // when:
        ResponseEntity<Person> personResponseEntity = restTemplate.getForEntity("http://localhost:8080/person/4", Person.class);

        // then:
        BDDAssertions.then(personResponseEntity.getStatusCodeValue()).isEqualTo(200);
        BDDAssertions.then(personResponseEntity.getBody().getId()).isEqualTo(4L);
        BDDAssertions.then(personResponseEntity.getBody().getName()).isEqualTo("Jack");
        BDDAssertions.then(personResponseEntity.getBody().getSurname()).isEqualTo("Black");

    }

    @Test
    public void get_person_from_kafka_contract() {
        this.trigger.trigger("trigger");

        Awaitility.await().untilAsserted(() -> {
            BDDAssertions.then(this.application.storedPerson).isNotNull();
            BDDAssertions.then(this.application.storedPerson.getName()).contains("Jack");
        });
    }
}