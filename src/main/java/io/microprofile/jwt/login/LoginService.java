package io.microprofile.jwt.login;

import io.microprofile.jwt.users.User;

public interface LoginService {

	LoginResponseDto doLogin(LoginRequestDto login) throws LoginException;
	
	void userValidationPassword(User user, LoginRequestDto login) throws LoginException;
	
	String passwordDecrypt(String passwordEncrypted);
}
