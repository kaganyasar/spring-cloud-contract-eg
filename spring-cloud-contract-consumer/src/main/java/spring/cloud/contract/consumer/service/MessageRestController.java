package spring.cloud.contract.consumer.service;

import data.Person;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
class MessageRestController {

    private final RestTemplate restTemplate;

    MessageRestController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping("/message/{personId}")
    String getMessage(@PathVariable("personId") Long personId) {
        Person person = this.restTemplate.getForObject("http://localhost:8081/person/{personId}", Person.class, personId);
        return "Hello " + person.getName();
    }

}