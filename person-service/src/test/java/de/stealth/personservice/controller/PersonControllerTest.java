package de.stealth.personservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.stealth.personservice.TestDataGenerator;
import de.stealth.personservice.entity.Address;
import de.stealth.personservice.entity.Person;
import de.stealth.personservice.service.PersonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @MockBean
    private Principal principal;

    private static Person person;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void setUp() {
        person = TestDataGenerator.generatePerson();
    }

    @Test
    void get_Id1001_ThenReturnPerson() throws Exception {
        int id = person.getId();

        given(personService.get(id)).willReturn(person);

        mockMvc.perform(
                get("/api/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())));
    }

    @Test
    void getPersonDetails_WithPrincipal_ThenReturnPerson() throws Exception {
        given(personService.getByPrincipal(any(Principal.class))).willReturn(person);

        mockMvc.perform(
                get("/api/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())));
    }

    @Test
    void save_Person_ThenReturnOk() throws Exception {
        String json = objectMapper.writeValueAsString(person);

        given(personService.save(person)).willReturn(person);

        mockMvc.perform(
                post("/api/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(status().isOk());
    }
}
