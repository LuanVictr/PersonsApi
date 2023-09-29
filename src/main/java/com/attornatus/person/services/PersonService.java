package com.attornatus.person.services;

import com.attornatus.person.exceptions.PersonNotFoundException;
import com.attornatus.person.model.entities.Address;
import com.attornatus.person.model.entities.Person;
import com.attornatus.person.model.repositories.AddressRepository;
import com.attornatus.person.model.repositories.PersonRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  private PersonRepository personRepository;

  private AddressRepository addressRepository;

  @Autowired
  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public Person createPerson(Person person) {
    return this.personRepository.save(person);
  }

  public Person getPersonById(Long id) throws PersonNotFoundException {
    Optional<Person> personOptional = this.personRepository.findById(id);

    if (personOptional.isEmpty()) {
      throw new PersonNotFoundException();
    }

    return personOptional.get();
  }

  public List<Person> getAllPersons() {
    return this.personRepository.findAll();
  }

  public Person updatePerson(Long personId, Person newPerson) throws PersonNotFoundException {

    Optional<Person> personOptional = this.personRepository.findById(personId);

    if (personOptional.isEmpty()) {
      throw new PersonNotFoundException();
    }

    Person person = personOptional.get();

    person.removeAddress(person.getAddress().get(0));
    person = newPerson;

    return this.personRepository.save(person);

  }
}

