package uni.miskolc.ips.ilona.tracking.persist;

import java.util.Collection;

import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.persist.exception.OperationExecutionErrorException;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserAlreadyExistsException;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserNotFoundException;

/**
 * 
 * @author Patrik / A5USL0
 *
 */
public interface UserManagementDAO {

	/*
	 * USER CRUD
	 */
	/**
	 * 
	 * @param user
	 * @throws UserAlreadyExistsException
	 * @throws OperationExecutionErrorException
	 */
	void createUser(UserData user) throws UserAlreadyExistsException, OperationExecutionErrorException;
	
	/**
	 * 
	 * @param userid
	 * @return
	 * @throws UserNotFoundException
	 * @throws OperationExecutionErrorException
	 */
	UserData getUser(String userid) throws UserNotFoundException, OperationExecutionErrorException;
	
	/**
	 * 
	 * @return
	 * @throws OperationExecutionErrorException
	 */
	Collection<UserData> getAllUsers() throws OperationExecutionErrorException;
	
	/**
	 * 
	 * @param user
	 * @throws UserNotFoundException
	 * @throws OperationExecutionErrorException
	 */
	void updateUser(UserData user) throws UserNotFoundException, OperationExecutionErrorException;

	/**
	 * 
	 * @param userid
	 * @throws UserNotFoundException
	 * @throws OperationExecutionErrorException
	 */
	void deleteUser(String userid) throws UserNotFoundException, OperationExecutionErrorException;
}
