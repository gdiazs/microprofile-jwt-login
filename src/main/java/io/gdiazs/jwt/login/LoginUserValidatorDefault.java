package io.gdiazs.jwt.login;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.gdiazs.jwt.crypto.PasswordEncoder;
import io.gdiazs.jwt.users.User;

@Singleton
public class LoginUserValidatorDefault implements LoginUserValidator{

	private PasswordEncoder passwordEncoder;
	
	public LoginUserValidatorDefault() {
		
	}
	
	@Inject
	public LoginUserValidatorDefault(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public boolean isUserValid(User user, String cleanPassword) {
		return passwordEncoder.verifyPassword(cleanPassword, user.getPassword());
	}
}
