package com.attornatus.person.controller;

import com.attornatus.person.exceptions.PersonNotFoundException;
import com.attornatus.person.model.dtos.AddressDto;
import com.attornatus.person.model.dtos.PersonDto;
import com.attornatus.person.model.dtos.PersonReturnedDto;
import com.attornatus.person.model.entities.Address;
import com.attornatus.person.model.entities.Person;
import com.attornatus.person.services.PersonService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Cria PersonController injetando personService utilizando injecao de dependencia.
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

  private final PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /**
   * Cria uma nova pessoa (Person).
   *
   * @param personDto camada Dto da entidade Person.
   * @return retorna um ResponseEntity utilizando a camada Dto de retorno de Person.
   */
  @PostMapping
  public ResponseEntity<PersonReturnedDto> createPerson(@RequestBody PersonDto personDto) {

    Person createdPerson = this.personService.createPerson(personDto.toEntity());

    Address mainAddress = createdPerson.getAddress().get(0);

    AddressDto addressDto = new AddressDto(mainAddress.getPublicPlace(),
        mainAddress.getPostalCode(), mainAddress.getNumber(),
        mainAddress.getCity());

    PersonReturnedDto personReturned = new PersonReturnedDto(createdPerson.getId(),
        createdPerson.getName(), createdPerson.getBirthDate(), createdPerson.getAddress());

    return ResponseEntity.status(HttpStatus.CREATED).body(personReturned);
  }

  @GetMapping("/{personId}")
  public ResponseEntity getPersonById(@PathVariable Long personId) {
    try {

      Person personFound = this.personService.getPersonById(personId);
      return ResponseEntity.status(HttpStatus.OK).body(personFound);

    } catch (PersonNotFoundException exception) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

  }

  @GetMapping
  public ResponseEntity<List<Person>> getAllPersons() {
    List<Person> allPersons = this.personService.getAllPersons();

    return ResponseEntity.status(HttpStatus.OK).body(allPersons);
  }

  @PutMapping("/{personId}")
  public ResponseEntity updatePerson(@PathVariable Long personId,
      @RequestBody PersonDto personInfo) {
    try {

      Person personUpdated = this.personService.updatePerson(personId, personInfo.toEntity());

      return ResponseEntity.status(HttpStatus.OK).body(personUpdated);

    } catch (PersonNotFoundException exception) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

  }

}

