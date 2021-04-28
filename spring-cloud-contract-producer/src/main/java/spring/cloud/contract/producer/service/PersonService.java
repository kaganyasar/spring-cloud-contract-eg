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
        personMap.put(1L, new Person(1L, "John", "Connor"));
        personMap.put(2L, new Person(2L, "Jim", "Josh"));
        personMap.put(3L, new Person(3L, "Matilda", "Cyber"));
        personMap.put(4L, new Person(4L, "Rich", "Gordon"));
    }

    public Person findPersonById(Long id) {
        return personMap.get(id);
    }

    public Person createNewPerson(Person person) {
        return personMap.put(person.getId(), person);
    }

    public Person createNewPerson2(Person person) {
        return personMap.put(person.getId(), person);
    }

    public Person updatePerson(Person person, Long personId) {
        person.setId(personId);
        return personMap.put(personId, person);
    }

    public void deletePerson(Long personId) throws Exception {
        personMap.remove(personId);
    }
}
