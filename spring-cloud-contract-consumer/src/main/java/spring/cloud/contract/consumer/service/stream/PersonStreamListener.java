package spring.cloud.contract.consumer.service.stream;

import data.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class PersonStreamListener {

    @Autowired
    PersonConsumerStream personConsumerStream;

    public Person storedPerson;

    @StreamListener(PersonConsumerStream.INPUT)
    public void getPerson(Person person) {
        storedPerson = person;
        System.out.println(person.getName());
    }
}
