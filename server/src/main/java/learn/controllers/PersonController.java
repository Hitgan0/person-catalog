package learn.controllers;

import learn.domain.PersonService;
import learn.domain.Result;
import learn.models.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/person")
public class PersonController {
    private final PersonService service;

    public PersonController(PersonService service) { this.service = service; }

    @GetMapping
    public List<Person> findAll() { return service.findAll(); }

    @GetMapping("/{personId}")
    public ResponseEntity<Person> findById(@PathVariable int personId) {
        Person person = service.findById(personId);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Person person) {
        Result<Person> result = service.add(person);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<Object> update(@PathVariable int personId, @RequestBody Person person) {
        if (personId != person.getPersonId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Person> result = service.update(person);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<Void> deleteById(@PathVariable int personId) {
        if (service.deleteById(personId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
