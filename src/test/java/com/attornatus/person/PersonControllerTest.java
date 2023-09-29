package com.attornatus.person;

import com.attornatus.person.controller.PersonController;
import com.attornatus.person.exceptions.PersonNotFoundException;
import com.attornatus.person.model.dtos.PersonDto;
import com.attornatus.person.model.dtos.PersonReturnedDto;
import com.attornatus.person.model.entities.Address;
import com.attornatus.person.model.entities.Person;
import com.attornatus.person.model.repositories.PersonRepository;
import com.attornatus.person.services.PersonService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class PersonControllerTest {

  @Autowired
  PersonController personController;

  @MockBean
  PersonRepository personRepository;

  @MockBean
  PersonService personService;

  @Test
  public void createPersonTest() {

    Person personMock = new Person(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim") );

    PersonDto personMockDto = new PersonDto(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim") );

    when(personService.createPerson(any(Person.class))).thenReturn(personMock);

    ResponseEntity<PersonReturnedDto> response = this.personController.createPerson(personMockDto);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    PersonReturnedDto personReturnedDto = response.getBody();

    assert personReturnedDto != null;
    Address address = personReturnedDto.addresses().get(0);

    assertEquals(personMockDto.id(), personReturnedDto.id());
    assertEquals(personMockDto.name(), personReturnedDto.name());
    assertEquals(personMockDto.birthDate(), personReturnedDto.birthDate());
    assertEquals(personMockDto.address().getPostalCode(), address.getPostalCode());
    assertEquals(personMockDto.address().getPublicPlace(), address.getPublicPlace());
    assertEquals(personMockDto.address().getNumber(), address.getNumber());
    assertEquals(personMockDto.address().getCity(), address.getCity());

  }

  @Test
  public void getPersonByIdSuccess() throws PersonNotFoundException {

    Person personMock = new Person(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim") );

    when(personService.getPersonById(1L)).thenReturn(personMock);

    ResponseEntity response = personController.getPersonById(1L);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(personMock, response.getBody());

  }

  @Test
  public void getAllPersonsTest() {
    List<Person> personListMock = List.of(new Person(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim") ));

    when(personService.getAllPersons()).thenReturn(personListMock);

    ResponseEntity<List<Person>> personsList = personController.getAllPersons();

    assertEquals(HttpStatus.OK, personsList.getStatusCode());
    assertEquals(personListMock, personsList.getBody());
  }

  /**
   * Para esses testes funcionarem é necessario que seja criada
   * uma entidade no banco de dados para realizar o update.
   * @throws PersonNotFoundException caso não seja encontrado nenhuma pessoa
   * com o id, retorna uma exception.
   */
  @Test
  public void updatePersonTest() throws PersonNotFoundException {
    PersonDto personDtoMock = new PersonDto(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim"));

    Person personToUpdate = new Person(1L, "Luan Victor de Araujo Silva", "21/03/1990",
        new Address("Rua da Rua de baixo", "88321-185", 308, "Sobral"));

    when(personService.updatePerson(1L, personDtoMock.toEntity())).thenReturn(personToUpdate);

    ResponseEntity personUpdated = this.personController.updatePerson(1L, personDtoMock);

    assertEquals(HttpStatus.OK, personUpdated.getStatusCode());

  }
}
