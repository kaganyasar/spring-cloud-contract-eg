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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import spring.cloud.contract.producer.service.PersonRestController;
import spring.cloud.contract.producer.service.PersonService;
import spring.cloud.contract.producer.service.ProducerApplication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@AutoConfigureMessageVerifier
@SpringBootTest(classes = ProducerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class PersonTest {

    @Autowired
    PersonRestController personRestController;

    @MockBean
    PersonService personService;

    @Before
    public void setup() throws Exception {

        //rest controllerımızı mocklayarak contract öncesi requestlerde bulunacağımız servisimizi hazır ediyoruz.
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(personRestController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        //findPersonById(4L) çağrıldığında döneceği person objesini söylüyoruz
        //controllerdan dönen bu obje contractta beklediğimiz response
        Mockito.when(personService.findPersonById(4L))
                .thenReturn(new Person(4L, "Jack", "Black"));

        //createNewPerson herhangi bir Person objesi ile çağrıldığında
        //return olarak Person objesi dönecek ve controller bu objeyi
        //döndüğü için contractta yazdığımız response u doğruluyacak.
        Mockito.when(personService.createNewPerson(any(Person.class)))
                .thenReturn(new Person(4L, "ExampleUser", "ExampleUser"));

        //createNewPerson2 herhangi bir Person objesi ile çağrıldığında
        //return olarak Person objesi dönecek ve controller Person Created dönmesini bekliyoruz
        Mockito.when(personService.createNewPerson2(any(Person.class)))
                .thenReturn(new Person(8L, "ExampleUser2", "ExampleUser2"));

        //createNewPerson2 herhangi bir person objesi aldığında null dönmesini söylüyoruz
        //controller da null döndüğü için Person Created mesajını yazmasını sağlıyacak.
        //Mockito.when(personService.createNewPerson2(any(Person.class))).thenReturn(null);

        //updatePerson herhangi bir person objesi ve personId:4 aldığında
        //thenReturn deki objeyi dönmesini söylüyoruz
        Mockito.when(personService.updatePerson(any(Person.class), eq(4L)))
                .thenReturn(new Person(4L, "Rick", "Gordon"));

        //DeletePerson personId:6 ile çağrıldığında exception fırlatacak ve
        //controller da exception yakalandığı için person not deleted mesajını
        //dönmesini sağlıyacak
        Mockito.doThrow(new Exception()).when(personService).deletePerson(6L);
        //deletePerson methodu personId:7 ile çağrıldığında exception fırlatmayacak
        //ve controller da person deleted mesajının dönmesini sağlıyacak
        Mockito.doNothing().when(personService).deletePerson(7L);
    }

    public void trigger() {
        this.personRestController.findPersonByIdAndSend(4L);
    }
}
