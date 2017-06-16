package com.dropbox.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class PasswordHasherTest {

	String password1 = "1452";
	String password2 = "ganzAnd3r3sPaswd!!!";
	
	@Test
	public void hashPasswordTest() {
		
		String hash1 = PasswordHasher.hashPassword(password1);
		String hash2 = PasswordHasher.hashPassword(password2);
		
		
		System.out.println(hash1);
		System.out.println(hash2);
		
		assertFalse(hash1.equals(hash2));
		
		assertTrue(PasswordHasher.checkPassword(hash1, password1));
		
		assertFalse(PasswordHasher.checkPassword(hash1, password2));
		
	}

}
