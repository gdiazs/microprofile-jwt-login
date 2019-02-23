package io.gdiazs.jwt.crypto;

public interface PasswordEncoder {
	
	String encode(String password);

	boolean verifyPassword(String password, String encrypted);
}
