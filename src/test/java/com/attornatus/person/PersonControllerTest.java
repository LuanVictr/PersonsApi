package com.attornatus.person;

import com.attornatus.person.exceptions.PersonNotFoundException;
import com.attornatus.person.model.dtos.PersonReturnedDto;
import com.attornatus.person.model.entities.Address;
import com.attornatus.person.model.entities.Person;
import com.attornatus.person.services.PersonService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

  @MockBean
  private PersonService personService;

  @Autowired
  private MockMvc mockMvc;


  @Test
  @DisplayName("Deve criar uma pessoa corretamente.")
  public void createPersonTest() throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();

    Person personMock = new Person(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim"));

    String personToCreateJson = """
        {
          "id": 1,
          "name": "Luan Victor",
          "birthDate": "1990-01-01",
          "address":
        		{
            "publicPlace": "123 Main St",
            "postalCode": "12345",
            "number": 42,
            "city": "Camocim"
        		}
        }
        """;

    when(personService.createPerson(any(Person.class))).thenReturn(personMock);

    MockHttpServletResponse response = mockMvc.perform(
        post("/persons")
            .content(personToCreateJson)
            .contentType(MediaType.APPLICATION_JSON)
    ).andReturn().getResponse();

    String responseJson = response.getContentAsString();

    PersonReturnedDto personReturned = objectMapper.readValue(responseJson, PersonReturnedDto.class);

    assertEquals(response.getStatus(), 201);
    assertEquals(personReturned.id(), personMock.getId());
    assertEquals(personReturned.name(), personMock.getName());
    assertEquals(personReturned.birthDate(), personMock.getBirthDate());
    assertEquals(personReturned.addresses().get(0).getCity(), personMock.getAddress().get(0).getCity());

  }

  @Test
  @DisplayName("Deve retornar uma pessoa quando seu id está correto")
  public void getPersonByIdSuccessTest() throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();

    Person personMock = new Person(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim"));

    when(personService.getPersonById(personMock.getId())).thenReturn(personMock);

    MockHttpServletResponse response = mockMvc.perform(
        get("/persons/" + personMock.getId())
    ).andReturn().getResponse();

    String responseJson = response.getContentAsString();

    Person personReturned = objectMapper.readValue(responseJson, Person.class);

    assertEquals(response.getStatus(), 200);
    assertEquals(personReturned.getId(), personMock.getId());
    assertEquals(personReturned.getName(), personMock.getName());
    assertEquals(personReturned.getBirthDate(), personMock.getBirthDate());

  }

  @Test
  @DisplayName("Deve retornar todas as pessoas cadastradas corretamente")
  public void getAllPersonsTest() throws Exception {

    List<Person> personListMock = List.of(new Person(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim") ));

    when(this.personService.getAllPersons()).thenReturn(personListMock);

    MockHttpServletResponse response = mockMvc.perform(
        get("/persons")
    ).andExpect(jsonPath("$[0].id").value(personListMock.get(0).getId()))
        .andExpect(jsonPath("$[0].name").value(personListMock.get(0).getName()))
        .andExpect(jsonPath("$[0].birthDate").value(personListMock.get(0).getBirthDate()))
        .andExpect(jsonPath("$[0].address[0].publicPlace")
            .value(personListMock.get(0).getAddress().get(0).getPublicPlace()))
        .andReturn().getResponse();

    assertEquals(response.getStatus(), 200);

  }

  @Test
  @DisplayName("Deve atualizar uma pessoa com sucesso")
  public void updatePersonTest() throws Exception {

    Person personMock = new Person(1L, "Luan Victor", "21/03/1990",
        new Address("Rua das ruas", "88938-231", 40, "Camocim"));

    String updatePersonInfoJson = """
      {
        "id": 1,
        "name": "Luan Victor de Araujo Silva",
        "birthDate": "21/03/2000",
        "address":
      		{
          "publicPlace": "Rua das ruas",
          "postalCode": "88938-231",
          "number": 308,
          "city": "Sobral"
      		}
      }
      """;

    when(this.personService.updatePerson(eq(personMock.getId()), any(Person.class))).thenReturn(personMock);

    MockHttpServletResponse response = mockMvc.perform(
        put("/persons/" + personMock.getId())
            .content(updatePersonInfoJson)
            .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(jsonPath("$.id").value(personMock.getId()))
        .andExpect(jsonPath("$.name").value(personMock.getName()))
        .andExpect(jsonPath("$.birthDate").value(personMock.getBirthDate()))
        .andReturn().getResponse();

    assertEquals(response.getStatus(), 200);

  }

}
//  @Test
//  public void updatePersonTest() throws PersonNotFoundException {
//    PersonDto personDtoMock = new PersonDto(1L, "Luan Victor", "21/03/1990",
//        new Address("Rua das ruas", "88938-231", 40, "Camocim"));
//
//    Person personToUpdate = new Person(1L, "Luan Victor de Araujo Silva", "21/03/1990",
//        new Address("Rua da Rua de baixo", "88321-185", 308, "Sobral"));
//
//    when(personService.updatePerson(1L, personDtoMock.toEntity())).thenReturn(personToUpdate);
//
//    ResponseEntity personUpdated = this.personController.updatePerson(1L, personDtoMock);
//
//    assertEquals(HttpStatus.OK, personUpdated.getStatusCode());
//
//  }
//}
