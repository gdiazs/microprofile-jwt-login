package io.gdiazs.jwt.tokens;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.RSASSASigner;

import io.gdiazs.jwt.users.User;

/**
 * com.nimbusds.jose Implementation
 * 
 * @author Guillermo
 *
 */
@Singleton
public class TokenServiceJose implements TokenService {

	private static final String HS_ALGORITHM = "HS";

	private static final String RS_ALGORITHM = "RS";

	@Inject
	@ConfigProperty(name = "microprofile.jwt.secret")
	private String secret;

	@Inject
	@ConfigProperty(name = "microprofile.jwt.privateKey")
	private String privateKeyPath;

	@Inject
	@ConfigProperty(name = "microprofile.jwt.algorithm")
	private String tokenType;

	@Inject
	@ConfigProperty(name = "microprofile.jwt.keyID")
	private String keyId;

	@Inject
	@ConfigProperty(name = "microprofile.jwt.aud")
	private String aud;

	@Inject
	@ConfigProperty(name = "microprofile.jwt.iss")
	private String iis;

	@Inject
	@ConfigProperty(name = "microprofile.jwt.expiration")
	private String expiration;

	
	
	
	public TokenServiceJose() {
	}

	@Override
	public String generateJWT(final User user) throws TokenException {

		final String tokenType = getTokenType();

		final Token token = new Token();
		token.setAud(this.aud);
		token.setSub(user.getUsername());
		token.setUpn(user.getUsername());
		token.setIss(this.iis);
		token.setJti(UUID.randomUUID().toString());
		token.setIat(System.currentTimeMillis());
		token.setExp(System.currentTimeMillis() + Integer.valueOf(expiration));
		
		token.setGroups(user.getRoles().stream().map(u -> u.getRole()).collect(Collectors.toList()));

		final JWSHeader header = new JWSHeader.Builder(new JWSAlgorithm(tokenType)).type(JOSEObjectType.JWT)
				.keyID(getKeyId()).build();

		final JWSObject jwsObject = new JWSObject(header, new Payload(token.toJSONString()));

		try {
			JWSSigner signer = null;
			if (tokenType.contains(RS_ALGORITHM)) {
				signer = new RSASSASigner(readPrivateKey());
			} else if (tokenType.contains(HS_ALGORITHM)) {
				signer = new MACSigner(getSecret());
			}
			jwsObject.sign(signer);

		} catch (JOSEException | IOException e) {
			throw new TokenException("Error generating the jwt", e.getCause());
		}

		return jwsObject.serialize();

	}

	private PrivateKey readPrivateKey() throws IOException {
		PEMParser pemParser = null;
		KeyPair kp = null;
		try {
			final InputStream inputStream = TokenServiceJose.class.getResourceAsStream(getPrivateKeyPath());
			pemParser = new PEMParser(new InputStreamReader(inputStream));
			final JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());
			final Object object = pemParser.readObject();
			kp = converter.getKeyPair((PEMKeyPair) object);
		} finally {
			if (null != pemParser) {
				pemParser.close();
			}

		}
		return kp.getPrivate();
	}

	public String getSecret() {
		return secret;
	}

	public String getPrivateKeyPath() {
		return privateKeyPath;
	}

	public String getTokenType() {
		return tokenType;
	}

	public String getKeyId() {
		return keyId;
	}

}
