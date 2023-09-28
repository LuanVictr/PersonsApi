package com.attornatus.person.model.dtos;

import com.attornatus.person.model.entities.Address;
import com.attornatus.person.model.entities.Person;

public record PersonDto(Long id, String name, String birthDate, Address address) {

  public Person toEntity() {
    return new Person(id, name, birthDate, address);
  }
}
