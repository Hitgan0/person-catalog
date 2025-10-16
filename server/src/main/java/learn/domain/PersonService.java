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

    public Result<Person> add(Person person) {
        Result<Person> result = validate(person);

        if (result.getType() != ResultType.SUCCESS) {
            return result;
        }

        // Email cannot be duplicated
        for (Person p: findAll()) {
            if (p.getEmail().equals(person.getEmail())) {
                result.addMessage("Email already exists (duplicate)", ResultType.INVALID);
                return result;
            }
        }

        if (person.getPersonId() != 0) {
            result.addMessage("personId cannot be set for 'add' operation", ResultType.INVALID);
            return result;
        }

        Person payload = personRepository.add(person);
        result.setPayload(payload);

        return result;
    }

    public Result<Person> update(Person person) {
        Result<Person> result = validate(person);

        if (result.getType() != ResultType.SUCCESS) {
            return result;
        }

        if (person.getPersonId() <= 0) {
            result.addMessage("personId must be set for 'update' operation", ResultType.INVALID);
            return result;
        }

        if (!personRepository.update(person)) {
            result.addMessage("Person ID: " + person.getPersonId() + " not found", ResultType.NOT_FOUND);
        } else {
            result.setPayload(person);
        }

        return result;
    }

    public boolean deleteById(int personId) { return personRepository.deleteById(personId); }

    private Result<Person> validate(Person person) {
        Result<Person> result = new Result<>();

        // Null check
        if (person == null) {
            result.addMessage("Person cannot be null", ResultType.INVALID);
            return result;
        }

        // First name cannot be null or blank
        if (Validations.isNullOrBlank(person.getFirstName())) {
            result.addMessage("First name is required", ResultType.INVALID);
        }

        // Last name cannot be null or blank
        if (Validations.isNullOrBlank(person.getLastName())) {
            result.addMessage("Last name is required", ResultType.INVALID);
        }

        // Email cannot be null or blank
        if (Validations.isNullOrBlank(person.getEmail())) {
            result.addMessage("Email is required", ResultType.INVALID);
        }

        // Phone cannot be null or blank
        if (Validations.isNullOrBlank(person.getPhone())) {
            result.addMessage("Phone is required", ResultType.INVALID);
        }

        return result;
    }
}
