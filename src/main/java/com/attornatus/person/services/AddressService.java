package com.attornatus.person.services;

import com.attornatus.person.exceptions.AddressNotFoundException;
import com.attornatus.person.exceptions.PersonNotFoundException;
import com.attornatus.person.model.entities.Address;
import com.attornatus.person.model.entities.Person;
import com.attornatus.person.model.repositories.AddressRepository;
import com.attornatus.person.model.repositories.PersonRepository;
import java.util.List;
import java.util.Objects;
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

    this.personRepository.save(person);

    return person.getAddress();
  }

  public List<Address> getAllAddresses(Long personId) throws PersonNotFoundException {
    Optional<Person> personOptional = this.personRepository.findById(personId);

    if (personOptional.isEmpty()) {
      throw new PersonNotFoundException();
    }

    return personOptional.get().getAddress();

  }

  public Address setAddressAsMain(Long addressId, Long personId)
      throws PersonNotFoundException, AddressNotFoundException {

    Optional<Person> personOptional = this.personRepository.findById(personId);

    if (personOptional.isEmpty()) {
      throw new PersonNotFoundException();
    }

    Person person = personOptional.get();

    List<Address> addresses = person.getAddress();

    Optional<Address> addressToSetAsMainOptional = addresses.stream().filter(address -> Objects.equals(
        address.getId(), addressId)).findFirst();

    if (addressToSetAsMainOptional.isEmpty()) {
      throw new AddressNotFoundException();
    }

    Address addressToSetAsMain = addressToSetAsMainOptional.get();
    addressToSetAsMain.setIsMain(true);

    this.addressRepository.save(addressToSetAsMain);

    return addressToSetAsMain;



  }

}
