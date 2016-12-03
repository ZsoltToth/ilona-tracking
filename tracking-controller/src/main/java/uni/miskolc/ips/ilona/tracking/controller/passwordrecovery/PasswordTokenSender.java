package uni.miskolc.ips.ilona.tracking.controller.passwordrecovery;

import uni.miskolc.ips.ilona.tracking.controller.exception.PasswordRecoveryTokenSenderException;

public interface PasswordTokenSender {

	void sendToken(String userid, String token, String address) throws PasswordRecoveryTokenSenderException;
	
	void sendNewPassword(String userid, String newPassword, String address) throws PasswordRecoveryTokenSenderException;
}
