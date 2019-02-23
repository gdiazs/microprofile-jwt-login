package io.gdiazs.jwt.login;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;

import io.gdiazs.jwt.crypto.PasswordEncoder;
import io.gdiazs.jwt.users.User;

@Default
@Named
public class LoginUserValidatorDefault implements LoginUserValidator{

	private PasswordEncoder passwordEncoder;
	
	@Inject
	public LoginUserValidatorDefault(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public boolean isUserValid(User user, String cleanPassword) {
		return passwordEncoder.verifyPassword(cleanPassword, user.getPassword());
	}




}
