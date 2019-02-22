package io.microprofile.jwt.tokens;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.PrivateKey;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;

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

import io.microprofile.jwt.users.User;

/**
 * com.nimbusds.jose Implementation
 * 
 * @author Guillermo
 *
 */
@Named
@Default
public class TokenServiceJose implements TokenService {

	@Inject
	@ConfigProperty(name = "{microprofile.jwt.secret}")
	private String secret;

	@Inject
	@ConfigProperty(name = "{microprofile.jwt.privateKey}")
	private String privateKeyPath;

	@Inject
	@ConfigProperty(name = "{microprofile.jwt.algorithm}")
	private String tokenType;
	
	
	@Inject
	@ConfigProperty(name = "{microprofile.jwt.keyID}")
	private String keyId;

	@Override
	public String generateJWT(User user, Token token) throws TokenException {

		final String tokenType = getTokenType();

		JWSHeader header = new JWSHeader.Builder(new JWSAlgorithm(tokenType)).type(JOSEObjectType.JWT).keyID(getKeyId())
				.build();

		JWSObject jwsObject = new JWSObject(header, new Payload(token.toJSONString()));

		try {
			JWSSigner signer = null;
			if (tokenType.contains("RS")) {
				signer = new RSASSASigner(readPrivateKey());
			} else if (tokenType.contains("HS")) {
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
			InputStream inputStream = TokenServiceJose.class.getResourceAsStream(getPrivateKeyPath());
			pemParser = new PEMParser(new InputStreamReader(inputStream));
			JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());
			Object object = pemParser.readObject();
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
