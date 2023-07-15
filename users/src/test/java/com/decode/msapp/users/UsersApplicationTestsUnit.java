package com.decode.msapp.users;


import com.decode.msapp.users.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UsersApplicationTestsUnit {

	private Person person;

	@BeforeEach
	public void setup() {
		person = new Person("Vasya" ,1900);
	}

	@Test
	void testGetFieldsPositive() throws Exception {
		var resName = person.getUsername();
		var resYear = person.getYearOfBirth();
		assertEquals("Vasya", resName);
		assertEquals(1900, resYear);
	}

	@Test
	void testSetNameNegative() {
		ArrayList<Person> emptyList = new ArrayList<Person>();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			var o = emptyList.get(0);
		});


	}


}
