package io.microprofile.jwt.login;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;

import io.microprofile.jwt.tokens.Token;
import io.microprofile.jwt.tokens.TokenException;
import io.microprofile.jwt.tokens.TokenService;
import io.microprofile.jwt.users.User;
import io.microprofile.jwt.users.UsersRepository;


@Named
@Default
public class LoginServiceImpl implements LoginService{

	private TokenService tokenService;
	
	private UsersRepository usersRepository;
	
	@Inject
	public LoginServiceImpl(TokenService tokenService, UsersRepository usersRepository) {
		this.tokenService = tokenService;
		this.usersRepository = usersRepository;
	}



	@Override
	public LoginResponseDto doLogin(LoginRequestDto login) throws LoginException {
		final User user = usersRepository.findUserByUsername(login.getUsername());
		
		this.userValidationPassword(user, login);
		String token = null;
		
		try {
			token = this.tokenService.generateJWT(user, new Token());
		} catch (TokenException e) {
			throw new LoginException(e.getCause());
		}
		
		return new LoginResponseDto(token);
	}



	@Override
	public void userValidationPassword(User user, LoginRequestDto login) throws LoginException {
		if(!passwordDecrypt(user.getPassword()).equals(login.getPassword())) {
			throw new LoginException("Invalid user credentials");
		}
		
	}

	@Override
	public String passwordDecrypt(String passwordEncrypted) {
		return passwordEncrypted;
	}




}
