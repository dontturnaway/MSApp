package com.decode.msapp.users;


import com.decode.msapp.users.models.User;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
class UsersApplicationTestsUnitParams {

	private User user;

	@BeforeEach
	void assignPerson() {
		user = new User();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "  "})
	void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input) {
		assertTrue(Strings.isBlank(input));
	};

	@ParameterizedTest
	@ValueSource(strings = {"Name1", "Name2"} )
	void testNameAssignPositive(String strings)  {
		user.setName(strings);
		assertEquals(strings, user.getName());
	}

	@ParameterizedTest
	@ValueSource(ints = {1900, 1800})
	void testYearAssignPositive(int values)  {
		user.setYearOfBirth(values);
		assertEquals(values, user.getYearOfBirth());
	}

	@ParameterizedTest
	@CsvSource({"Name,1900", "Name1,2000", "Name2,3000"})
	void testFieldsAssignPositive(String name, int value)  {
		user.setName(name);
		user.setYearOfBirth(value);
		assertEquals(name, user.getName());
		assertEquals(value, user.getYearOfBirth());
	}

	@ParameterizedTest
	@MethodSource("provideParameters")
	void testParametersFromMethod(String name, int value) {
		user.setName(name);
		user.setYearOfBirth(value);
		assertEquals(name, user.getName());
		assertEquals(value, user.getYearOfBirth());
	}

	private static Stream<Arguments> provideParameters() {
		return Stream.of(
				Arguments.of("Name1", 1000),
				Arguments.of("Name2", 2000),
				Arguments.of("Name3", 3000)
		);
	}

}
