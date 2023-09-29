package com.attornatus.person;

import com.attornatus.person.exceptions.PersonNotFoundException;
import com.attornatus.person.model.entities.Address;
import com.attornatus.person.model.entities.Person;
import com.attornatus.person.model.repositories.PersonRepository;
import com.attornatus.person.services.PersonService;
import java.util.List;
import java.util.Optional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PersonServiceTest {

  @Autowired
  private PersonService personService;

  @MockBean
  PersonRepository personRepository;

  @Test
  public void createPersonTest() {
    Person personMock = new Person(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim") );

    when(personRepository.save(personMock)).thenReturn(personMock);

    Person createdPerson = this.personService.createPerson(personMock);

    verify(personRepository, times(1)).save(personMock);

    assertEquals(personMock.getName(), createdPerson.getName());
    assertEquals(personMock.getId(), createdPerson.getId());
    assertEquals(personMock.getBirthDate(), createdPerson.getBirthDate());
    assertEquals(personMock.getAddress(), createdPerson.getAddress());
  }

  @Test
  public void getPersonByIdTest() throws PersonNotFoundException {
    Person personMock = new Person(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim") );

    when(personRepository.findById(1L)).thenReturn(Optional.of(personMock));

    Person person = this.personService.getPersonById(1L);

    assertEquals(person.getName(), personMock.getName());
    assertEquals(person.getAddress(), personMock.getAddress());
    assertEquals(person.getBirthDate(), personMock.getBirthDate());
    assertEquals(person.getAddress(), personMock.getAddress());
    assertThrows(PersonNotFoundException.class, () -> {
      this.personService.getPersonById(999L);
    });
  }

  @Test
  public void getAllPersonsTest() {
    List<Person> personListMock = List.of(new Person(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim") ));

    when(personRepository.findAll()).thenReturn(personListMock);

    List<Person> persons = personService.getAllPersons();

    assertEquals(personListMock, persons);
  }

  /**
   * Para esses testes funcionarem é necessario que seja criada
   * uma entidade no banco de dados para realizar o update.
   * @throws PersonNotFoundException caso não seja encontrado nenhuma pessoa
   * com o id, retorna uma exception.
   */
  @Test
  public void updatePersonTest() throws PersonNotFoundException {
    Person existingPerson = new Person(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim") );

    Person updatedPerson = new Person(1L, "Luan Victor de Araujo Silva", "21/03/2000",
        new Address("Rua das ruas", "88938-231", 308, "Sobral") );

    when(personRepository.findById(1L)).thenReturn(Optional.of(existingPerson));

    Person result = personService.updatePerson(1L, updatedPerson);

    assertEquals(updatedPerson, result);
  }

}
