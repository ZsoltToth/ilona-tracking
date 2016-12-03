package uni.miskolc.ips.ilona.tracking.controller.passwordrecovery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uni.miskolc.ips.ilona.tracking.controller.exception.PasswordRecoveryException;
import uni.miskolc.ips.ilona.tracking.controller.exception.PasswordRecoveryTokenRestoreErrorException;
import uni.miskolc.ips.ilona.tracking.controller.exception.PasswordRecoveryTokenStorageErrorException;
import uni.miskolc.ips.ilona.tracking.model.PasswordRecoveryToken;
import uni.miskolc.ips.ilona.tracking.persist.SecurityFunctionsDAO;

public class MysqlMybatisPasswordTokenDAOImpl implements PasswordTokenDAO {

	private static Logger logger = LogManager.getLogger(MysqlMybatisPasswordTokenDAOImpl.class);

	private SecurityFunctionsDAO tokenRecoveryManager;

	public MysqlMybatisPasswordTokenDAOImpl() {

	}

	public MysqlMybatisPasswordTokenDAOImpl(SecurityFunctionsDAO tokenRecoveryManager) {
		this.tokenRecoveryManager = tokenRecoveryManager;
	}

	@Override
	public void storePasswordToken(PasswordRecoveryToken token) throws PasswordRecoveryTokenStorageErrorException {
		try {
			tokenRecoveryManager.storePasswordResetToken(token);
		} catch (Exception e) {
			String tokenMessage = "Token is null!";
			if (token != null) {
				tokenMessage = token.toString();
			}

			logger.error("Recovery token storage failed! Token details:  " + tokenMessage);
			throw new PasswordRecoveryTokenStorageErrorException(
					"Recovery token storage failed! Token details:  " + tokenMessage, e);
		}

	}

	@Override
	public PasswordRecoveryToken loadPasswordToken(PasswordRecoveryToken passwordToken)
			throws PasswordRecoveryTokenRestoreErrorException {
		PasswordRecoveryToken token = null;
		try {
			token = tokenRecoveryManager.restorePasswordResetToken(passwordToken);
		} catch (Exception e) {
			String tokenMessage = "Token is null!";
			if (passwordToken != null) {
				tokenMessage = passwordToken.toString();
			}
			logger.error(
					"Recovery token restore failed! Token details:  " + tokenMessage);
			throw new PasswordRecoveryTokenRestoreErrorException(
					"Recovery token restore failed! Token details:  " + tokenMessage, e);
		}
		return token;
	}

	@Override
	public void deletePasswordRecoveryToken(String userid) throws PasswordRecoveryException {
		try {
			tokenRecoveryManager.deletePasswordRecoveryToken(userid);
		} catch (Exception e) {
			logger.error("Recovery token restore failed! userid: " + userid);
			throw new PasswordRecoveryException("Recovery token restore failed! userid: " + userid, e);
		}
	}
}
