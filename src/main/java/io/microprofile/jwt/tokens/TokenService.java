package io.microprofile.jwt.tokens;

import io.microprofile.jwt.users.User;

public interface TokenService {

	String generateJWT(User user, Token token) throws TokenException;
	
	String getSecret();

	String getPrivateKeyPath();

	String getTokenType();
}
