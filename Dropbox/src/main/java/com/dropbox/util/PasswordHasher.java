package com.dropbox.util;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * Utility Class for hashing Passwords
 * 
 * @author David Schick
 */
public class PasswordHasher
{
	private static final SecureRandom randomGenerator = new SecureRandom();
	private static final String ALGORITHM = "SHA-256";
	
	/**
	 * Generates Random two-Byte value for salting the hashes
	 * 
	 * @return random two-byte value
	 */
	private static byte [] nextSalt()
	{
		byte [] salt = new byte[2];
		randomGenerator.nextBytes(salt);
		return salt;
	}
	
	/**
	 * Generates SHA256 hash out of a String and a random salt
	 * 
	 * @param pwd string to hash
	 * @return hex-representation of hashed string
	 */
	public static String hashPassword(String pwd)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			byte [] salt = nextSalt();
			md.reset();
			md.update(salt);
			byte [] hashed = md.digest(pwd.getBytes());
			return Hex.encodeHexString(salt) + '%' + Hex.encodeHexString(hashed);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * checks if a String is generating a certain hash
	 * 
	 * @param hashed hashed string
	 * @param pwd string to check
	 * @return true if pwd generates same hash, else false
	 */
	public static boolean checkPassword(String hashed, String pwd)
	{
		try
		{
			String [] strings = hashed.split("%");
			byte [] salt = Hex.decodeHex(strings[0].toCharArray());
			byte [] hash = Hex.decodeHex(strings[1].toCharArray());
			
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			md.reset();
			md.update(salt);
			if (Arrays.equals(md.digest(pwd.getBytes()), hash))
				return true;
			else 
				return false;
		}
		catch (NoSuchAlgorithmException | DecoderException e)
		{
			e.printStackTrace();
			return false;
		}
	}
}