package de.stealth.personservice.controller;

import de.stealth.personservice.entity.Address;
import de.stealth.personservice.entity.Person;
import de.stealth.personservice.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private Person person;
    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setId(997);
        address.setStreet("Sedulinos");
        address.setCity("Visaginas");
        address.setZip("1234");
        address.setCountry("Lithuania");

        person = new Person();
        person.setId(1001);
        person.setFirstName("Dmitrij");
        person.setLastName("Gusev");
        person.setEmail("a@a.de");
        person.setAddress(address);
    }

    @Test
    void get_id1001_ThenReturnPerson() throws Exception {
        given(personService.get(any(Integer.class))).willReturn(person);

        mockMvc.perform(
                (get("/api/1001")
                .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())));


    }

    @Test
    void getDetails() {
    }

    @Test
    void save() {
    }
}
