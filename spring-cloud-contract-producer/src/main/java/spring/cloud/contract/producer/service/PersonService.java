package spring.cloud.contract.producer.service;

import data.Person;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PersonService {
    private final Map<Long, Person> personMap;

    public PersonService() {
        this.personMap = new HashMap<>();
        personMap.put(1L, new Person(1L, "Oguz", "Yasar"));
        personMap.put(2L, new Person(2L, "Kagan", "Yasar"));
        personMap.put(3L, new Person(3L, "Muslum", "Yasar"));
        personMap.put(4L, new Person(4L, "Ali", "Veli"));
    }

    public Person findPersonById(Long id) {
        return personMap.get(id);
    }
}
