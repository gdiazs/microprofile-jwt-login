package io.microprofile.jwt.users;

public interface UsersRepository {

	User findUserByUsername(String username);
}
