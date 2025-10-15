package learn.models;

import java.time.LocalDate;
import java.util.Objects;

public class Person {
    private int personId;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
    private String phone;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personId == person.personId && firstName.equals(person.firstName) && lastName.equals(person.lastName) && dob.isEqual(person.dob) && email.equals(person.email) && phone.equals(person.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, firstName, lastName, dob, email, phone);
    }
}
