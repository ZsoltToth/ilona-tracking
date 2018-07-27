package uni.miskolc.ips.ilona.tracking.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uni.miskolc.ips.ilona.tracking.model.DeviceData;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.persist.UserAndDeviceDAO;
import uni.miskolc.ips.ilona.tracking.persist.exception.DeviceAlreadyExistsException;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserAlreadyExistsException;
import uni.miskolc.ips.ilona.tracking.service.UserAndDeviceService;
import uni.miskolc.ips.ilona.tracking.service.exceptions.*;

import java.util.Collection;

public class UserAndDeviceServiceImpl implements UserAndDeviceService {

    private static Logger logger = LogManager.getLogger(UserAndDeviceServiceImpl.class);

    private UserAndDeviceDAO userDeviceDAO;

    public UserAndDeviceServiceImpl() {
        this.userDeviceDAO = null;
    }

    public UserAndDeviceServiceImpl(UserAndDeviceDAO dao) {
        this.userDeviceDAO = dao;
    }

    @Override
    public void createUser(UserData user) throws DuplicatedUserException, ServiceGeneralErrorException {
        try {
            userDeviceDAO.createUser(user);
        } catch (UserAlreadyExistsException e) {
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("User creation failed! userid: " + userid);
            throw new DuplicatedUserException("Duplicated user with id: " + userid, e);
        } catch (Exception e) {
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("User creation failed, general error! userid: " + userid);
            throw new ServiceGeneralErrorException("User creation failed, general error! userid: " + userid, e);
        }

    }

    @Override
    public UserData getUser(String userid) throws UserNotFoundException, ServiceGeneralErrorException {
        UserData user = null;

        try {
            user = userDeviceDAO.getUser(userid);
        } catch (uni.miskolc.ips.ilona.tracking.persist.exception.UserNotFoundException e) {
            logger.error("No user exists with id: " + userid);
            throw new UserNotFoundException("No user exists with id: " + userid, e);
        } catch (Exception e) {
            logger.error("User load failed general error! ");
            throw new ServiceGeneralErrorException("User load failed, general error! ", e);
        }
        return user;
    }

    @Override
    public Collection<UserData> getAllUsers() throws ServiceGeneralErrorException {
        Collection<UserData> users = null;

        try {
            users = userDeviceDAO.getAllUsers();
        } catch (Exception e) {
            logger.error("There was an error while laoding users!");
            throw new ServiceGeneralErrorException("There was an error while laoding users!", e);
        }
        return users;
    }

    @Override
    public void updateUser(UserData user) throws UserNotFoundException, ServiceGeneralErrorException {
        try {
            userDeviceDAO.updateUser(user);
        } catch (uni.miskolc.ips.ilona.tracking.persist.exception.UserNotFoundException e) {
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("User not found with id: " + userid);
            throw new UserNotFoundException("User not found with id: " + userid, e);
        } catch (Exception e) {
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("User not found with id: " + userid);
            throw new ServiceGeneralErrorException("User not found with id: " + userid, e);
        }

    }

    @Override
    public void deleteUser(UserData user) throws UserNotFoundException, ServiceGeneralErrorException {
        try {
            userDeviceDAO.deleteUser(user.getUserid());
        } catch (uni.miskolc.ips.ilona.tracking.persist.exception.UserNotFoundException e) {
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("User not found with id: " + userid);
            throw new UserNotFoundException("User not found with id: " + userid, e);
        } catch (Exception e) {
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("User not found with id: " + userid);
            throw new ServiceGeneralErrorException("User not found with id: " + userid, e);
        }

    }

    @Override
    public void storeDevice(DeviceData device, UserData user)
            throws UserNotFoundException, DuplicatedDeviceException, ServiceGeneralErrorException {

        try {
            userDeviceDAO.storeDevice(device, user);
        } catch (DeviceAlreadyExistsException e) {
            String deviceid = "NULL";
            if (device != null) {
                deviceid = device.getDeviceid();
            }
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("Device is already exists with id: " + deviceid + " ownerid: " + userid);
            throw new DuplicatedDeviceException("Device is already exists with id: " + deviceid + " ownerid: " + userid,
                    e);
        } catch (Exception e) {
            String deviceid = "NULL";
            if (device != null) {
                deviceid = device.getDeviceid();
            }
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("Service error deviceid: " + deviceid + " ownerid: " + userid);
            throw new ServiceGeneralErrorException("Service error deviceid: " + deviceid + " ownerid: " + userid, e);
        }

    }

    @Override
    public Collection<DeviceData> readUserDevices(UserData user)
            throws UserNotFoundException, ServiceGeneralErrorException {
        Collection<DeviceData> devices = null;
        try {
            devices = userDeviceDAO.readUserDevices(user);
        } catch (Exception e) {
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("Device load error! userid: " + userid);
            throw new ServiceGeneralErrorException("Device load error! userid: " + userid, e);
        }
        return devices;
    }

    @Override
    public void updateDevice(DeviceData device, UserData user)
            throws DeviceNotFoundException, UserNotFoundException, ServiceGeneralErrorException {
        try {
            userDeviceDAO.updateDevice(device, user);
        } catch (uni.miskolc.ips.ilona.tracking.persist.exception.DeviceNotFoundException e) {
            String deviceid = "NULL";
            if (device != null) {
                deviceid = device.getDeviceid();
            }
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("Device has not found id: " + deviceid + " ownerid: " + userid);
            throw new DeviceNotFoundException("Device has not found id: " + deviceid + " ownerid: " + userid, e);
        } catch (Exception e) {
            String deviceid = "NULL";
            if (device != null) {
                deviceid = device.getDeviceid();
            }
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("Device has not found id: " + deviceid + " ownerid: " + userid);
            throw new ServiceGeneralErrorException("Device has not found id: " + deviceid + " ownerid: " + userid, e);
        }

    }

    @Override
    public void deleteDevice(DeviceData device, UserData user)
            throws DeviceNotFoundException, UserNotFoundException, ServiceGeneralErrorException {
        try {
            userDeviceDAO.deleteDevice(device, user);
        } catch (uni.miskolc.ips.ilona.tracking.persist.exception.DeviceNotFoundException e) {
            String deviceid = "NULL";
            if (device != null) {
                deviceid = device.getDeviceid();
            }
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("Device has not found id: " + deviceid + " ownerid: " + userid);
            throw new DeviceNotFoundException("Device has not found id: " + deviceid + " ownerid: " + userid, e);
        } catch (Exception e) {
            String deviceid = "NULL";
            if (device != null) {
                deviceid = device.getDeviceid();
            }
            String userid = "NULL";
            if (user != null) {
                userid = user.getUserid();
            }
            logger.error("Device has not found id: " + deviceid + " ownerid: " + userid);
            throw new ServiceGeneralErrorException("Device has not found id: " + deviceid + " ownerid: " + userid, e);
        }
    }
}