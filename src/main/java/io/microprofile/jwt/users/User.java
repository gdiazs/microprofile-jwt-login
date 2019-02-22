package io.microprofile.jwt.users;

import java.util.Collection;

import io.microprofile.jwt.roles.Role;

public interface User {

	String getUsername();
	
	String getPassword();
	
	Collection<Role> getRoles();
}
