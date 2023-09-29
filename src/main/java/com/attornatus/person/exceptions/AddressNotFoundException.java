package com.attornatus.person.exceptions;

public class AddressNotFoundException extends Exception {
  public AddressNotFoundException() {
    super("Endereco nao encontrado!");
  }
}
