package learn.domain;

import learn.data.PersonRepository;
import learn.models.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() { return personRepository.findAll(); }

    public Person findById(int personId) { return personRepository.findById(personId); }
}
