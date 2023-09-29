package com.attornatus.person.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String publicPlace;
  private String postalCode;
  private int number;

  private String city;

  @Column(name = "is_main")
  private Boolean isMain;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "person_id")
  private Person person;

  public Address() {}

  public Address(String publicPlace, String postalCode,
      int number, String city) {

    this.publicPlace = publicPlace;
    this.postalCode = postalCode;
    this.number = number;
    this.city = city;
    this.isMain = false;
  }

  public Long getId() {
    return id;
  }

  public Boolean getIsMain() {
    return isMain;
  }

  public void setIsMain(Boolean main) {
    isMain = main;
  }

  public void setId(Long id) {
    this.id = id;
  }
  public String getPublicPlace() {
    return publicPlace;
  }

  public void setPublicPlace(String publicPlace) {
    this.publicPlace = publicPlace;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }
}
