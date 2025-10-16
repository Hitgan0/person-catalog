package learn.data;

import learn.models.Person;
import learn.models.mappers.PersonMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonJdbcTemplateRepository implements PersonRepository{
    private final JdbcTemplate jdbcTemplate;

    public PersonJdbcTemplateRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public List<Person> findAll() {
        final String sql = "select person_id, first_name, last_name, dob, email, phone " +
                "from person;";

        return jdbcTemplate.query(sql, new PersonMapper());
    }

    @Override
    public Person findById(int personId) {
        final String sql = "select person_id, first_name, last_name, dob, email, phone " +
                "from person " +
                "where person_id = ?;";

        return jdbcTemplate.query(sql, new PersonMapper(), personId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public Person add(Person person) {
        return null;
    }

    @Override
    public boolean update(Person person) {
        return false;
    }

    @Override
    public boolean deleteById(int personId) {
        return false;
    }
}
