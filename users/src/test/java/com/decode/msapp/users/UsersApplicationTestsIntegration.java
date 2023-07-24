package com.decode.msapp.users;

import com.decode.msapp.users.controllers.UserController;
import com.decode.msapp.users.model.User;
import com.decode.msapp.users.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.decode.msapp.users.services.UserService;
import com.decode.msapp.users.services.UserCredentialsService;
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

@WebMvcTest(controllers = UserController.class)
@Import({SecurityConfig.class})
class UsersApplicationTestsIntegration {

	@Autowired(required = false)
	private MockMvc mockMvc;
	@Autowired(required = false)
	private ObjectMapper mapper;

	@MockBean
	UserRepository peopleRepository;
	@MockBean
	UserCredentialsService userCredentialsService;
	@MockBean
	UserService userService;

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
		User testUser = new User();
		testUser.setName("MOCK_USER");
		testUser.setPassword("123");
		testUser.setRole("ROLE_USER");
		testUser.setYearOfBirth(1901);
		mockMvc.perform(MockMvcRequestBuilders.post("/peoplerest")
				.content(mapper.writeValueAsString(testUser))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	void createPersonNegative() throws Exception {
		User emptyTestUser = new User();
		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/peoplerest")
				.content(mapper.writeValueAsString(emptyTestUser))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@ParameterizedTest
	@ValueSource(strings = {"Vasya", "Petya"})
	void createPersonValues(String names) throws Exception {
		User emptyTestUser = new User();
		emptyTestUser.setName(names);
		mockMvc.perform(MockMvcRequestBuilders.post("/peoplerest")
						.content(mapper.writeValueAsString(emptyTestUser))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

}
