package spring.cloud.contract.consumer.test;

import data.Person;
import org.assertj.core.api.BDDAssertions;
import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import spring.cloud.contract.consumer.service.ConsumerApplication;
import spring.cloud.contract.consumer.service.stream.PersonStreamListener;
import wiremock.net.minidev.json.JSONObject;

@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "org.example:spring-cloud-contract-producer:+:stubs:8080")
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

    @Test
    public void create_new_person_service_contract() {
        // given:
        String url = "http://localhost:8080/person/createNewPerson";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("id", "4");
        personJsonObject.put("name", "ExampleUser");
        personJsonObject.put("surname", "ExampleUser");
        HttpHeaders  headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toJSONString(), headers);
        // when:
        ResponseEntity<Person> personResponseEntity = restTemplate.postForEntity(url, request, Person.class);
        // then:
        BDDAssertions.then(personResponseEntity.getStatusCodeValue()).isEqualTo(200);
        BDDAssertions.then(personResponseEntity.getBody().getId()).isEqualTo(4L);
        BDDAssertions.then(personResponseEntity.getBody().getName()).isEqualTo("ExampleUser");
        BDDAssertions.then(personResponseEntity.getBody().getSurname()).isEqualTo("ExampleUser");
    }

    @Test
    public void create_new_person2_service_contract() {
        // given:
        String url = "http://localhost:8080/person/createNewPerson2";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("id", "8");
        personJsonObject.put("name", "ExampleUser2");
        personJsonObject.put("surname", "ExampleUser2");
        HttpHeaders  headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString(), headers);
        // when:
        ResponseEntity<String> personResponseEntity = restTemplate.postForEntity(url, request, String.class);
        // then:
        BDDAssertions.then(personResponseEntity.getStatusCodeValue()).isEqualTo(200);
        BDDAssertions.then(personResponseEntity.getBody()).isEqualTo("Person Created");
    }

    @Test
    public void delete_person_throw_service_contract() {
        // given:
        String url = "http://localhost:8080/person/delete/6";
        RestTemplate restTemplate = new RestTemplate();
        // when:
        ResponseEntity<String> personResponseEntity = restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity(null,null), String.class);
        // then:
        BDDAssertions.then(personResponseEntity.getStatusCodeValue()).isEqualTo(200);
        BDDAssertions.then(personResponseEntity.getBody()).isEqualTo("Person Not Deleted");
    }

    @Test
    public void delete_person_and_dont_throw_service_contract() {
        // given:
        String url = "http://localhost:8080/person/delete/7";
        RestTemplate restTemplate = new RestTemplate();
        // when:
        ResponseEntity<String> personResponseEntity = restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(null, null), String.class);
        // then:
        BDDAssertions.then(personResponseEntity.getStatusCodeValue()).isEqualTo(200);
        BDDAssertions.then(personResponseEntity.getBody()).isEqualTo("Person Deleted");
    }

    @Test
    public void update_person_service_contract() {
        // given:
        String url = "http://localhost:8080/person/update/4";
        RestTemplate restTemplate = new RestTemplate();
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("name", "BenFero");
        personJsonObject.put("surname", "BenFero");
        HttpHeaders  headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString(), headers);

        // when:
        ResponseEntity<Person> personResponseEntity = restTemplate.exchange(url,HttpMethod.PUT, request, Person.class);

        // then:
        BDDAssertions.then(personResponseEntity.getStatusCodeValue()).isEqualTo(200);
        BDDAssertions.then(personResponseEntity.getBody().getId()).isEqualTo(4L);
        BDDAssertions.then(personResponseEntity.getBody().getName()).isEqualTo("BenFero");
        BDDAssertions.then(personResponseEntity.getBody().getSurname()).isEqualTo("BenFero");
    }
}