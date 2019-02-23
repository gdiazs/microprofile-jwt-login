package io.gdiazs.jwt.crypto;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordEncoderDefault implements PasswordEncoder{

	private Integer salt;
	
	public PasswordEncoderDefault(Integer salt) {
		super();
		this.salt = salt;
	}


	@Override
	public String encode(String password) {
		char[] bcryptChars = BCrypt.with(BCrypt.Version.VERSION_2B).hashToChar(this.salt, password.toCharArray());
		return new String (bcryptChars);
	}


	@Override
	public boolean verifyPassword(String password, String encodedPassword) {
		BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), encodedPassword.toCharArray());
		
		return result.verified;
	}

}
