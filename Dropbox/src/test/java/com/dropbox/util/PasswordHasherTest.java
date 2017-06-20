package com.dropbox.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class PasswordHasherTest {

	String rightPassword = "testlangespw";
	String wrongPassword = "ganzAnd3r3sPaswd!!!";
	
	@Test
	public void hashPasswordTest() {
		
		String hash1 = PasswordHasher.hashPassword(rightPassword);
		String hash2 = PasswordHasher.hashPassword(wrongPassword);
		
		
		System.out.println(hash1);
		System.out.println(hash2);
		
		assertFalse(hash1.equals(hash2));
		
		assertTrue(PasswordHasher.checkPassword(hash1, rightPassword));
		
		assertFalse(PasswordHasher.checkPassword(hash1, wrongPassword));
		
	}

}
