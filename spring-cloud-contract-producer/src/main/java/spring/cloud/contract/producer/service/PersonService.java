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

    public Person createNewPerson(Person person) {
        return personMap.put(person.getId(), person);
    }

    public Person createNewPerson2(Person person){
        return personMap.put(person.getId(), person);
    }

    public Person updatePerson(Person person,Long personId) {
        person.setId(personId);
        return personMap.put(personId,person);
    }

    public void deletePerson(Long personId) throws Exception{
        personMap.remove(personId);
    }
}
