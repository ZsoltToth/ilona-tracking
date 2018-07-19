package uni.miskolc.ips.ilona.tracking.service;

import java.util.Collection;

import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.service.exceptions.DuplicatedUserException;
import uni.miskolc.ips.ilona.tracking.service.exceptions.ServiceGeneralErrorException;
import uni.miskolc.ips.ilona.tracking.service.exceptions.UserNotFoundException;

public interface UserService {

	void createUser(UserData user) throws DuplicatedUserException, ServiceGeneralErrorException;

	UserData getUser(String userid) throws UserNotFoundException, ServiceGeneralErrorException;

	Collection<UserData> getAllUsers() throws ServiceGeneralErrorException;

	void updateUser(UserData user) throws UserNotFoundException, ServiceGeneralErrorException;

	void deleteUser(UserData user) throws UserNotFoundException, ServiceGeneralErrorException;
}