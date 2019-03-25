package io.gdiazs.jwt.login;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.gdiazs.jwt.tokens.TokenException;
import io.gdiazs.jwt.tokens.TokenService;
import io.gdiazs.jwt.users.User;
import io.gdiazs.jwt.users.UserService;


@Singleton
public class LoginServiceImpl implements LoginService{

	private TokenService tokenService;
	
	private UserService userServicce;
	
	private LoginUserValidator loginUserValidator;
	

	public LoginServiceImpl() {
	}
	
	@Inject
	public LoginServiceImpl(TokenService tokenService, UserService userServicce,
			LoginUserValidator loginUserValidator) {
		super();
		this.tokenService = tokenService;
		this.userServicce = userServicce;
		this.loginUserValidator = loginUserValidator;
	}




	@Override
	public LoginResponseDto doLogin(LoginRequestDto login) throws LoginException {
		final User user = userServicce.findUserByUsername(login.getUsername());
		String token = null;
		
		if(this.loginUserValidator.isUserValid(user, login.getPassword())) {
			
			try {
				token = this.tokenService.generateJWT(user);
			} catch (TokenException e) {
				throw new LoginException(e.getCause());
			}
			
			
		}else {
			throw new LoginException("Bad credentials!");
		}
		

		return new LoginResponseDto(token);
	}







}
