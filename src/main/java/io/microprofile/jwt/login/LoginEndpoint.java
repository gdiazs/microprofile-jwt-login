package io.microprofile.jwt.login;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author gdiazs
 */
@Path(LoginPaths.AUTH)
@RequestScoped
public class LoginEndpoint {
	
	private final LoginService loginService;
	
	@Inject
	public LoginEndpoint(final LoginService loginService) {
		this.loginService = loginService;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response doLogin(final LoginRequestDto login) throws LoginException {
		
		
		final LoginResponseDto doLogin = loginService.doLogin(login);
		
		return Response.ok(doLogin).build();
	}

}
