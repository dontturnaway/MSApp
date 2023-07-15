package com.decode.msapp.users;


import com.decode.msapp.users.models.Person;
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

	private Person person;

	@BeforeEach
	void assignPerson() {
		person = new Person();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "  "})
	void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input) {
		assertTrue(Strings.isBlank(input));
	};

	@ParameterizedTest
	@ValueSource(strings = {"Name1", "Name2"} )
	void testNameAssignPositive(String strings)  {
		person.setUsername(strings);
		assertEquals(strings, person.getUsername());
	}

	@ParameterizedTest
	@ValueSource(ints = {1900, 1800})
	void testYearAssignPositive(int values)  {
		person.setYearOfBirth(values);
		assertEquals(values, person.getYearOfBirth());
	}

	@ParameterizedTest
	@CsvSource({"Name,1900", "Name1,2000", "Name2,3000"})
	void testFieldsAssignPositive(String name, int value)  {
		person.setUsername(name);
		person.setYearOfBirth(value);
		assertEquals(name, person.getUsername());
		assertEquals(value, person.getYearOfBirth());
	}

	@ParameterizedTest
	@MethodSource("provideParameters")
	void testParametersFromMethod(String name, int value) {
		person.setUsername(name);
		person.setYearOfBirth(value);
		assertEquals(name, person.getUsername());
		assertEquals(value, person.getYearOfBirth());
	}

	private static Stream<Arguments> provideParameters() {
		return Stream.of(
				Arguments.of("Name1", 1000),
				Arguments.of("Name2", 2000),
				Arguments.of("Name3", 3000)
		);
	}

}
