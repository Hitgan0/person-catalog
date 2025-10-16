package learn.data;

import learn.models.Person;
import learn.models.mappers.PersonMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
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
        final String sql = "insert into person (first_name, last_name, dob, email, phone) " +
                "values(?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setObject(3, person.getDob());
            ps.setString(4, person.getEmail());
            ps.setString(5, person.getPhone());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        person.setPersonId(keyHolder.getKey().intValue());
        return person;
    }

    @Override
    public boolean update(Person person) {
        final String sql = "update person set "
                         + "first_name = ?, "
                         + "last_name = ?, "
                         + "dob = ?, "
                         + "email = ?, "
                         + "phone = ? "
                         + "where person_id = ?;";

        return jdbcTemplate.update(sql,
                person.getFirstName(),
                person.getLastName(),
                person.getDob(),
                person.getEmail(),
                person.getPhone(),
                person.getPersonId()) > 0;
    }

    @Override
    public boolean deleteById(int personId) {
        return jdbcTemplate.update("delete from person where person_id = ?;", personId) > 0;
    }
}
