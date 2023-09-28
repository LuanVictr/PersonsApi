package com.attornatus.person.model.dtos;

import com.attornatus.person.model.entities.Address;
import java.util.List;

public record PersonReturnedDto(Long id, String name, String birthDate, List<Address> addresses) {
}
