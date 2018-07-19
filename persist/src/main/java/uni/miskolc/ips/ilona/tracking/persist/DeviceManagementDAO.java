package uni.miskolc.ips.ilona.tracking.persist;

import java.util.Collection;

import uni.miskolc.ips.ilona.tracking.model.DeviceData;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.persist.exception.DeviceAlreadyExistsException;
import uni.miskolc.ips.ilona.tracking.persist.exception.DeviceNotFoundException;
import uni.miskolc.ips.ilona.tracking.persist.exception.OperationExecutionErrorException;

/**
 * 
 * @author Patrik / A5USL0
 *
 */
public interface DeviceManagementDAO {

	/**
	 * 
	 * @param device
	 * @param user
	 * @throws DeviceAlreadyExistsException
	 * @throws OperationExecutionErrorException
	 */
	void storeDevice(DeviceData device, UserData user)
			throws DeviceAlreadyExistsException, OperationExecutionErrorException;

	/**
	 * 
	 * @param userid
	 * @param user
	 * @return
	 * @throws OperationExecutionErrorException
	 */
	Collection<DeviceData> readUserDevices(UserData user) throws OperationExecutionErrorException;

	/**
	 * 
	 * @param device
	 * @param user
	 * @throws DeviceNotFoundException
	 * @throws OperationExecutionErrorException
	 */
	void updateDevice(DeviceData device, UserData user)
			throws DeviceNotFoundException, OperationExecutionErrorException;

	/**
	 * 
	 * @param device
	 * @param user
	 * @throws DeviceNotFoundException
	 * @throws OperationExecutionErrorException
	 */
	void deleteDevice(DeviceData device, UserData user)
			throws DeviceNotFoundException, OperationExecutionErrorException;

}
