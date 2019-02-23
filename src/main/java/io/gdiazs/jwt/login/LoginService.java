package io.gdiazs.jwt.login;

public interface LoginService {
	LoginResponseDto doLogin(LoginRequestDto login) throws LoginException;
}
