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
public class AddressService {

  private AddressRepository addressRepository;

  private PersonRepository personRepository;

  @Autowired
  public AddressService(AddressRepository addressRepository,
      PersonRepository personRepository) {
    this.addressRepository = addressRepository;
    this.personRepository = personRepository;
  }

  public List<Address> addNewAddresses(Long id, Address newAddress) throws PersonNotFoundException {

    Optional<Person> personOptional = this.personRepository.findById(id);

    if (personOptional.isEmpty()) {
      throw new PersonNotFoundException();
    }

    Person person = personOptional.get();

    Address newAddressCreated = this.addressRepository.save(newAddress);

        person.addAddress(newAddressCreated);

    return person.getAddress();
  }

}
