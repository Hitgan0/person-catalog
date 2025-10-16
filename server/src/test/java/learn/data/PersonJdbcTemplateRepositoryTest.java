package learn.data;

import learn.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PersonJdbcTemplateRepositoryTest {
    int NEXT_ID = 4;

    @Autowired
    PersonJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() { knownGoodState.set(); }

    @Test
    void shouldFindAll() {
        List<Person> persons = repository.findAll();
        assertNotNull(persons);
        assertEquals(3, persons.size());
    }

    @Test
    void shouldFindById() {
        Person person = repository.findById(3);
        assertEquals(3, person.getPersonId());
        assertEquals("Peter", person.getFirstName());
        assertEquals("Parker", person.getLastName());
    }

    @Test
    void shouldAdd() {
        Person person = makePerson();
        Person actual = repository.add(person);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getPersonId());
    }

    @Test
    void shouldUpdate() {
        Person person = makePerson();
        person.setPersonId(2);
        assertTrue(repository.update(person));
        person.setPersonId(999);
        assertFalse(repository.update(person));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(1));
    }

    private Person makePerson() {
        Person person = new Person();
        person.setFirstName("Bruce");
        person.setLastName("Wayne");
        person.setDob(LocalDate.of(2002, 3, 3));
        person.setEmail("wayne@email.com");
        person.setPhone("123-456-7890");

        return person;
    }
}