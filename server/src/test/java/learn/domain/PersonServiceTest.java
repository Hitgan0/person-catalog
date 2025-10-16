package learn.domain;

import learn.data.PersonRepository;
import learn.models.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PersonServiceTest {
    @Autowired
    PersonService service;

    @MockBean
    PersonRepository repository;

    @Test
    void shouldFindAll() {
        List<Person> expected = List.of(makePerson());
        when(repository.findAll()).thenReturn(expected);
        List<Person> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {
        Person expected = makePerson();
        expected.setPersonId(1);
        when(repository.findById(1)).thenReturn(expected);
        Person actual = service.findById(1);
        assertEquals(expected, actual);
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