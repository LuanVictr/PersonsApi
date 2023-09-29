package com.attornatus.person.controller;

import com.attornatus.person.exceptions.AddressNotFoundException;
import com.attornatus.person.exceptions.PersonNotFoundException;
import com.attornatus.person.model.entities.Address;
import com.attornatus.person.services.AddressService;
import com.attornatus.person.services.PersonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/{personId}")
  public ResponseEntity getAllAddresses(@PathVariable Long personId) {
    try {

      List<Address> addresses = this.addressService.getAllAddresses(personId);

      return ResponseEntity.status(HttpStatus.OK).body(addresses);

    } catch (PersonNotFoundException exception) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
  }

  @PostMapping("/main/{personId}/{addressId}")
  public ResponseEntity setAddressAsMAin(@PathVariable Long personId,
      @PathVariable Long addressId) {

    try {

      Address addressChanged = this.addressService.setAddressAsMain(addressId, personId);
      return ResponseEntity.status(HttpStatus.OK).body(addressChanged);

    } catch (PersonNotFoundException personNotFoundException) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(personNotFoundException.getMessage());

    } catch (AddressNotFoundException addressNotFoundException) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(addressNotFoundException);
    }

  }

}
