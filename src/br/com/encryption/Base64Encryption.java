package br.com.encryption;

import java.util.Base64;

public class Base64Encryption
{

	public String encodePassword(String pass_word) throws NullPointerException
	{
		
		String encodedPassword = null;
		
		try
		{
			
		encodedPassword = new String(Base64.getEncoder().encodeToString(pass_word.getBytes()));
		
		} catch(NullPointerException e)
		{	
			e.printStackTrace();
		}
		
		return encodedPassword;
	}
}