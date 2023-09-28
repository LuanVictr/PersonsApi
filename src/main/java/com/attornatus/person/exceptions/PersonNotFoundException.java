package com.attornatus.person.exceptions;

public class PersonNotFoundException extends Exception {

  private final int statusCode = 404;
  public PersonNotFoundException() {
    super("Pessoa não encontrada!");
  }
}
