package spring.cloud.contract.producer.service;

import data.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.cloud.contract.producer.service.stream.PersonStreamSender;

@RestController
public class PersonRestController {

    @Autowired
    PersonStreamSender personStreamSender;

    @Autowired
    PersonService personService;

    @GetMapping("/person/{id}")
    public Person findPersonById(@PathVariable("id") Long id) {
        return personService.findPersonById(id);
    }

    @GetMapping("/personsend/{id}")
    public Person findPersonByIdAndSend(@PathVariable("id") Long id) {
        Person person = personService.findPersonById(id);
        personStreamSender.sendPerson(person);
        return person;
    }

    @PostMapping(path = "/person/createNewPerson")
    public Person createNewPerson(@RequestBody Person person) {
        return personService.createNewPerson(person);
    }


    @PostMapping(path = "/person/createNewPerson2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createNewPerson2(@RequestBody Person person) {
        Person person1 = personService.createNewPerson2(person);
        if(person1 == null){
            return ResponseEntity.ok().body("Person Not Created");
        }else{
            return ResponseEntity.ok().body("Person Created");
        }
    }

    @PutMapping(path = "/person/update/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Person updatePerson(@RequestBody Person person, @PathVariable("personId") Long personId) {
        //return new Person(personId,"BenFero","BenFero");
        return personService.updatePerson(person, personId);
    }
    @DeleteMapping(path = "person/delete/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePerson(@PathVariable("personId") Long personId){
        try{
            personService.deletePerson(personId);
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.ok().body("Person Not Deleted");
        }
        return ResponseEntity.ok().body("Person Deleted");
    }
}
