package com.attornatus.person.exceptions;

public class PersonNotFoundException extends Exception {
  public PersonNotFoundException() {
    super("Pessoa não encontrada!");
  }
}
