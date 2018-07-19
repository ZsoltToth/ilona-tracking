package uni.miskolc.ips.ilona.tracking.controller.passwordrecovery;

import java.util.UUID;

/**
 * 
 * @author Patrik
 *
 */
public class UUIDBasedPasswordTokenGenerator implements PasswordTokenGenerator {

	public UUIDBasedPasswordTokenGenerator() {

	}

	@Override
	public String generateToken() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
