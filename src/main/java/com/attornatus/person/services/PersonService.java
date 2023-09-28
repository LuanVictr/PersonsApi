package com.attornatus.person.services;

import com.attornatus.person.model.entities.Person;
import com.attornatus.person.model.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  private PersonRepository personRepository;

  @Autowired
  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public Person createPerson(Person person) {
    return this.personRepository.save(person);
  }
}
