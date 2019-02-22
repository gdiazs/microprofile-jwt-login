package io.microprofile.jwt.users;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.inject.Default;
import javax.inject.Named;

import io.microprofile.jwt.users.UserInMemory.UserBuilder;

@Named
@Default
public class UsersInMemoryRepository implements UsersRepository {

	private Collection<User> users;

	public UsersInMemoryRepository() {
		UserBuilder userBuilder = new UserBuilder();
		this.users = userBuilder.addUser("gdiazs", "Test1234", "admin", "supervisor")
				.addUser("memo", "Test12345", "basic").build();
	}

	@Override
	public User findUserByUsername(String username) {
		Optional<User> findAny = users.stream().filter(user -> user.getUsername().equals(username)).findAny();
		if (findAny.isPresent()) {
			return findAny.get();
		}
		return null;
	}

}
