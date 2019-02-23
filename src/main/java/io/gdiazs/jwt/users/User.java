package io.gdiazs.jwt.users;

import java.util.Collection;

import io.gdiazs.jwt.roles.Role;

public interface User {

	String getUsername();
	
	String getPassword();
	
	Collection<Role> getRoles();
}
