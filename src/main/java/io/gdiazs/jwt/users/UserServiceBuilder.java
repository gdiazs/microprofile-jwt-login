package io.gdiazs.jwt.users;

import java.util.Optional;

public class UserServiceBuilder {

	private static UserBuilder userBuilder;

	private static UserService inMemoryUserService;

	public synchronized static UserBuilder userBuilder() {
		if (null == userBuilder) {
			userBuilder = new UserBuilder();
		}
		return userBuilder;
	}

	public synchronized static UserService build() {
		if (null == inMemoryUserService) {
			inMemoryUserService = new UserService() {

				@Override
				public User findUserByUsername(String username) {
					final Optional<User> findAny = userBuilder.getUsers().stream()
							.filter(user -> user.getUsername().equals(username)).findAny();

					if (findAny.isPresent()) {
						return findAny.get();
					}
					return null;
				}
			};
		}
		return inMemoryUserService;
	}

}
