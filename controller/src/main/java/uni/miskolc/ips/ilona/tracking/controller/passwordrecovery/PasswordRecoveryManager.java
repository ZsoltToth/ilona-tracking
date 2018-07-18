package uni.miskolc.ips.ilona.tracking.controller.passwordrecovery;

import uni.miskolc.ips.ilona.tracking.controller.exception.PasswordRecoveryException;
import uni.miskolc.ips.ilona.tracking.controller.exception.PasswordRecoveryTokenValidityErrorException;
import uni.miskolc.ips.ilona.tracking.model.UserData;

public interface PasswordRecoveryManager {

	void handlePasswordRecoveryRequest(UserData user) throws PasswordRecoveryException;

	void handlePasswordRestore(UserData userid, String token)
			throws PasswordRecoveryTokenValidityErrorException, PasswordRecoveryException;
}
