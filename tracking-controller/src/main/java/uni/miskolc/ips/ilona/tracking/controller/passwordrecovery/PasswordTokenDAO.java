package uni.miskolc.ips.ilona.tracking.controller.passwordrecovery;

import uni.miskolc.ips.ilona.tracking.controller.exception.PasswordRecoveryException;
import uni.miskolc.ips.ilona.tracking.controller.exception.PasswordRecoveryTokenRestoreErrorException;
import uni.miskolc.ips.ilona.tracking.controller.exception.PasswordRecoveryTokenStorageErrorException;
import uni.miskolc.ips.ilona.tracking.model.PasswordRecoveryToken;

public interface PasswordTokenDAO {

	void storePasswordToken(PasswordRecoveryToken token) throws PasswordRecoveryTokenStorageErrorException;

	PasswordRecoveryToken loadPasswordToken(PasswordRecoveryToken passwordToken)
			throws PasswordRecoveryTokenRestoreErrorException;
	
	void deletePasswordRecoveryToken(String userid) throws PasswordRecoveryException;
}
