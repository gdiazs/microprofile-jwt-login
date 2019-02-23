package io.gdiazs.jwt.login;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author gdiazs
 */
@Path(LoginPaths.AUTH)
@Singleton
public class LoginEndpoint {
	
	private LoginService loginService;
	
	@Inject
	public LoginEndpoint(LoginService loginService) {
		this.loginService = loginService;
	}
	
	

	public LoginEndpoint() {
	}


	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response doLogin(final LoginRequestDto login) throws LoginException {
		
		final LoginResponseDto doLogin = loginService.doLogin(login);
		
		return Response.ok(doLogin).build();
	}

}
