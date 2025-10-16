package learn.models.mappers;

import learn.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setPersonId(rs.getInt("person_id"));
        person.setFirstName(rs.getString("first_name"));
        person.setLastName(rs.getString("last_name"));
        person.setDob(rs.getDate("dob").toLocalDate());
        person.setEmail(rs.getString("email"));
        person.setPhone(rs.getString("phone"));

        return person;
    }
}
