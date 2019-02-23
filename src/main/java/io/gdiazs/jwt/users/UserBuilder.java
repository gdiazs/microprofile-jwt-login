package io.gdiazs.jwt.users;

import java.util.ArrayList;
import java.util.Collection;

public final class UserBuilder{
	
	private Collection<User> users;
	
	public UserBuilder() {
		this.users = new ArrayList<>();
	}

	public UserBuilder addUser(String username, String password, String ... roles) {
		
		users.add(new UserInMemory(username, password, roles));
		return this;
	}
	
	protected Collection<User> getUsers(){
		return this.users;
	}
	
	
}