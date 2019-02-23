package io.gdiazs.jwt.login;

import io.gdiazs.jwt.users.User;

public interface LoginUserValidator {
	

	boolean isUserValid(User user, String encodedPasswords);
	
}
