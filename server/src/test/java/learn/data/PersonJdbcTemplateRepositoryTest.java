package learn.data;

import learn.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PersonJdbcTemplateRepositoryTest {
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
}