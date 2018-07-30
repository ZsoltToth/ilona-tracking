package uni.miskolc.ips.ilona.tracking.persist;

import uni.miskolc.ips.ilona.tracking.model.PasswordRecoveryToken;
import uni.miskolc.ips.ilona.tracking.persist.exception.OperationExecutionErrorException;
import uni.miskolc.ips.ilona.tracking.persist.exception.PasswordRecoveryTokenNotFoundException;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserNotFoundInDatabaseException;

import java.util.Collection;
import java.util.Date;

public interface SecurityFunctionsDAO {

    public void updatePassword(String userid, String hashedPassword)
            throws UserNotFoundInDatabaseException, OperationExecutionErrorException;

    public void updateEnabled(String userid, boolean enabled)
            throws UserNotFoundInDatabaseException, OperationExecutionErrorException;

    public void updateAccountExpiration(String userid, Date expiration)
            throws UserNotFoundInDatabaseException, OperationExecutionErrorException;

    public void updateRoles(String userid, Collection<String> roles)
            throws UserNotFoundInDatabaseException, OperationExecutionErrorException;

    public int eraseBadLogins(String userid) throws UserNotFoundInDatabaseException, OperationExecutionErrorException;

    public void updateBadLogins(String userid, Collection<Date> badLogins)
            throws UserNotFoundInDatabaseException, OperationExecutionErrorException;

    public void updateLockedAndUntilLocked(String userid, boolean nonLocked, Date lockedUntil, boolean deleteBadLogins)
            throws UserNotFoundInDatabaseException, OperationExecutionErrorException;

    public void storePasswordResetToken(PasswordRecoveryToken token) throws OperationExecutionErrorException;

    public PasswordRecoveryToken restorePasswordResetToken(PasswordRecoveryToken token)
            throws PasswordRecoveryTokenNotFoundException, OperationExecutionErrorException;

    public void deletePasswordRecoveryToken(String userid) throws OperationExecutionErrorException;
}
