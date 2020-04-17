package spring.cloud.contract.producer.test;

import data.Person;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.junit4.SpringRunner;
import spring.cloud.contract.producer.service.PersonRestController;
import spring.cloud.contract.producer.service.PersonService;
import spring.cloud.contract.producer.service.ProducerApplication;

@RunWith(SpringRunner.class)
@AutoConfigureMessageVerifier
@SpringBootTest(classes = ProducerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class PersonTest {
    @Autowired
    PersonRestController personRestController;

    @MockBean
    PersonService personService;

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(personRestController);

        Mockito.when(personService.findPersonById(4L))
                .thenReturn(new Person(4L, "Jack", "Black"));
    }

    public void trigger() {
        this.personRestController.findPersonByIdAndSend(4L);
    }

}
