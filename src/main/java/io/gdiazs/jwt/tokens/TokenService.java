package io.gdiazs.jwt.tokens;

import io.gdiazs.jwt.users.User;

public interface TokenService {

	String generateJWT(User user) throws TokenException;
	
	String getSecret();

	String getPrivateKeyPath();

	String getTokenType();
}
