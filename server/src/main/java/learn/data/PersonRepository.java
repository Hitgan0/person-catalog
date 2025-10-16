package learn.data;

import learn.models.Person;

import java.util.List;

public interface PersonRepository {
    List<Person> findAll();

    Person findById(int personId);

    Person add(Person person);

    boolean update(Person person);

    boolean deleteById(int personId);
}
