package uni.miskolc.ips.ilona.tracking.persist;

import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.persist.exception.OperationExecutionErrorException;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserAlreadyExistsException;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserNotFoundInDatabaseException;

import java.util.Collection;

/**
 * @author Patrik / A5USL0
 */
public interface UserManagementDAO {

    /*
     * USER CRUD
     */

    /**
     * @param user
     * @throws UserAlreadyExistsException
     * @throws OperationExecutionErrorException
     */
    void createUser(UserData user) throws UserAlreadyExistsException, OperationExecutionErrorException;

    /**
     * @param userid
     * @return
     * @throws UserNotFoundInDatabaseException
     * @throws OperationExecutionErrorException
     */
    UserData getUser(String userid) throws UserNotFoundInDatabaseException, OperationExecutionErrorException;

    /**
     * @return
     * @throws OperationExecutionErrorException
     */
    Collection<UserData> getAllUsers() throws OperationExecutionErrorException;

    /**
     * @param user
     * @throws UserNotFoundInDatabaseException
     * @throws OperationExecutionErrorException
     */
    void updateUser(UserData user) throws UserNotFoundInDatabaseException, OperationExecutionErrorException;

    /**
     * @param userid
     * @throws UserNotFoundInDatabaseException
     * @throws OperationExecutionErrorException
     */
    void deleteUser(String userid) throws UserNotFoundInDatabaseException, OperationExecutionErrorException;
}
