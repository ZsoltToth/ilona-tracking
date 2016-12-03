package uni.miskolc.ips.ilona.tracking.controller.passwordrecovery;

import java.util.UUID;

public class SimpleUUIDBasedPasswordGenerator implements PasswordGenerator {

	@Override
	public String generatePassword(int length) {
		if (length > 30) {
			length = 30;
		}
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, length);
	}

}
