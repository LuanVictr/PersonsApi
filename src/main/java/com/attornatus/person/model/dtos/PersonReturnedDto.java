package com.attornatus.person.model.dtos;

public record PersonReturnedDto(Long id, String name, String birthDate, AddressDto addressDto) {
}
