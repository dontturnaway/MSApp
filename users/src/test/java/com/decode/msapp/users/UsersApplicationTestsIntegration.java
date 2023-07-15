package com.decode.msapp.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.decode.msapp.users.config.SecurityConfig;
import com.decode.msapp.users.controllers.PeopleRESTController;
import com.decode.msapp.users.models.Person;
import com.decode.msapp.users.repositories.PeopleRepository;
import com.decode.msapp.users.services.PeopleService;
import com.decode.msapp.users.services.PersonDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PeopleRESTController.class)
@Import({SecurityConfig.class})
class UsersApplicationTestsIntegration {

	@Autowired(required = false)
	private MockMvc mockMvc;
	@Autowired(required = false)
	private ObjectMapper mapper;

	@MockBean
	PeopleRepository peopleRepository;
	@MockBean
	PersonDetailsService personDetailsService;
	@MockBean
	PeopleService peopleService;

	@Test
	void getAllPersons() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/peoplerest"))
				.andExpect(status().isOk());
	}

	@Test
	void getPersonById() throws Exception {
		int existingId = 1;
		mockMvc.perform(MockMvcRequestBuilders.get("/peoplerest/"+existingId))
				.andExpect(status().isOk());
	}

	@Test
	void createPersonPositive() throws Exception {
		Person testPerson = new Person();
		testPerson.setUsername("MOCK_USER");
		testPerson.setPassword("123");
		testPerson.setRole("ROLE_USER");
		testPerson.setYearOfBirth(1901);
		mockMvc.perform(MockMvcRequestBuilders.post("/peoplerest")
				.content(mapper.writeValueAsString(testPerson))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	void createPersonNegative() throws Exception {
		Person emptyTestPerson = new Person();
		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/peoplerest")
				.content(mapper.writeValueAsString(emptyTestPerson))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@ParameterizedTest
	@ValueSource(strings = {"Vasya", "Petya"})
	void createPersonValues(String names) throws Exception {
		Person emptyTestPerson = new Person();
		emptyTestPerson.setUsername(names);
		mockMvc.perform(MockMvcRequestBuilders.post("/peoplerest")
						.content(mapper.writeValueAsString(emptyTestPerson))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}


}
