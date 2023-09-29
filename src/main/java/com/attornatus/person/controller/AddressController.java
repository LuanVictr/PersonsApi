package com.attornatus.person.controller;

import com.attornatus.person.exceptions.PersonNotFoundException;
import com.attornatus.person.model.entities.Address;
import com.attornatus.person.services.AddressService;
import com.attornatus.person.services.PersonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

  private AddressService addressService;

  private PersonService personService;

  @Autowired
  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }

  @PostMapping("/{personId}")
  public ResponseEntity createAddress(@PathVariable Long personId, @RequestBody Address address) {

    try {

      List<Address> addresses = this.addressService.addNewAddresses(personId, address);

      return ResponseEntity.status(HttpStatus.CREATED).body(addresses);

    } catch (PersonNotFoundException exception) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());

    }

  }

}
