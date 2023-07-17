package com.decode.msapp.users;


import com.decode.msapp.users.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UsersApplicationTestsUnit {

	private User user;

	@BeforeEach
	public void setup() {
		user = new User("Vasya" ,1900);
	}

	@Test
	void testGetFieldsPositive() throws Exception {
		var resName = user.getName();
		var resYear = user.getYearOfBirth();
		assertEquals("Vasya", resName);
		assertEquals(1900, resYear);
	}

	@Test
	void testSetNameNegative() {
		ArrayList<User> emptyList = new ArrayList<User>();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			var o = emptyList.get(0);
		});


	}


}
