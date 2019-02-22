package io.microprofile.jwt.users;

import java.util.ArrayList;
import java.util.Collection;

import io.microprofile.jwt.roles.Role;

public final class UserInMemory implements User {

	final private String username;

	final private String password;

	final private Collection<Role> roles;

	public UserInMemory(String username, String password, String... roles) {
		this.username = username;
		this.password = password;
		
		this.roles = new ArrayList<>();
		
		for (String role : roles) {
			this.roles.add(new Role() {
				@Override
		
				public String getRole() {
					return role;
				}
			});
		}

	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}
	
	
	public static class UserBuilder{
		
		private Collection<User> users;
		
		public UserBuilder() {
			this.users = new ArrayList<>();
		}

		public UserBuilder addUser(String username, String password, String ... roles) {
			
			users.add(new UserInMemory(username, password, roles));
			return this;
		}
		
		public Collection<User> build(){
			return this.users;
		}
		
		
	}

}
