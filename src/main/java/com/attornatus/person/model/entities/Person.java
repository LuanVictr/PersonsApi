package com.attornatus.person.model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String birthDate;

  @OneToOne(cascade = CascadeType.ALL)
  private Address address;

  public Person() {}

  public Person(Long id, String name, String birthDate, String publicPlace,
  String postalCode, int number, String city) {
    this.id = id;
    this.name = name;
    this.birthDate = birthDate;
    this.address = new Address(publicPlace, postalCode, number, city);
  }

}
