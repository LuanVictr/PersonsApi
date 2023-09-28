package com.attornatus.person.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String birthDate;

  @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Address> addresses = new ArrayList<>();

  public Person() {}

  public Person(Long id, String name, String birthDate, Address address) {
    this.id = id;
    this.name = name;
    this.birthDate = birthDate;
    this.addAddress(address);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public List<Address> getAddress() {
    return addresses;
  }

  public void addAddress(Address address) {
    this.addresses.add(address);
    address.setPerson(this);
  }

  public void removeAddress(Address address) {
    this.addresses.remove(address);
    address.setPerson(null);
  }
}
