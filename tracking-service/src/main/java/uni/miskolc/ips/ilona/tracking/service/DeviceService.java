package uni.miskolc.ips.ilona.tracking.service;

import java.util.Collection;

import uni.miskolc.ips.ilona.tracking.model.DeviceData;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.service.exceptions.DeviceNotFoundException;
import uni.miskolc.ips.ilona.tracking.service.exceptions.DuplicatedDeviceException;
import uni.miskolc.ips.ilona.tracking.service.exceptions.ServiceGeneralErrorException;
import uni.miskolc.ips.ilona.tracking.service.exceptions.UserNotFoundException;

public interface DeviceService {

	void storeDevice(DeviceData device, UserData user)
			throws UserNotFoundException, DuplicatedDeviceException, ServiceGeneralErrorException;

	Collection<DeviceData> readUserDevices(UserData user) throws UserNotFoundException, ServiceGeneralErrorException;

	void updateDevice(DeviceData device, UserData user)
			throws DeviceNotFoundException, UserNotFoundException, ServiceGeneralErrorException;

	void deleteDevice(DeviceData device, UserData user)
			throws DeviceNotFoundException, UserNotFoundException, ServiceGeneralErrorException;

}