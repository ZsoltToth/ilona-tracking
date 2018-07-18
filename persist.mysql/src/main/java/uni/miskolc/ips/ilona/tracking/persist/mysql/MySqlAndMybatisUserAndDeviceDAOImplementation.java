package uni.miskolc.ips.ilona.tracking.persist.mysql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import uni.miskolc.ips.ilona.tracking.model.DeviceData;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.persist.UserAndDeviceDAO;
import uni.miskolc.ips.ilona.tracking.persist.exception.DeviceAlreadyExistsException;
import uni.miskolc.ips.ilona.tracking.persist.exception.DeviceNotFoundException;
import uni.miskolc.ips.ilona.tracking.persist.exception.OperationExecutionErrorException;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserAlreadyExistsException;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserNotFoundException;
import uni.miskolc.ips.ilona.tracking.persist.mysql.mappers.UserAndDeviceMapper;

public class MySqlAndMybatisUserAndDeviceDAOImplementation implements UserAndDeviceDAO {

	private static Logger logger = LogManager.getLogger(MySqlAndMybatisUserAndDeviceDAOImplementation.class);

	private SqlSessionFactory sessionFactory;

	public MySqlAndMybatisUserAndDeviceDAOImplementation() {

	}

	public MySqlAndMybatisUserAndDeviceDAOImplementation(final String configFilePath, final String host, final int port,
			final String database, final String user, final String password) throws FileNotFoundException {
		File configFile = new File(configFilePath);
		// new File("src/main/resources/mybatis-configuration.xml");
		InputStream inputStream = new FileInputStream(configFile);
		String urlPattern = "jdbc:mysql://%s:%s/%s";
		String connectionURL = String.format(urlPattern, host, port, database);
		Properties props = new Properties();
		props.put("driver", "com.mysql.jdbc.Driver");
		props.put("url", connectionURL);
		props.put("username", user);
		props.put("password", password);

		this.sessionFactory = (new SqlSessionFactoryBuilder()).build(inputStream, props);
	}

	/*
	 * USER CRUD PART
	 */

	@Override
	public void createUser(UserData user) throws UserAlreadyExistsException, OperationExecutionErrorException {
		SqlSession session = sessionFactory.openSession();
		try {
			UserAndDeviceMapper mapper = session.getMapper(UserAndDeviceMapper.class);
			mapper.createUserBaseData(user);
			// mapper.eraseUserRoles(user.getUserid()); // empty roles
			mapper.createUserRoles(user);
			// mapper.storeLoginAttempts(user); // empty login attemps
			session.commit();

		} catch (Exception e) {
			if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
				String userid = "NULL";
				if (user != null) {
					userid = user.getUserid();
				}
				logger.error("Duplicated user with id: " + userid + "  Error: " + e.getMessage());
				throw new UserAlreadyExistsException("User already exists: " + user.getUserid());
			}
			logger.error("Error: " + e.getMessage());
			throw new OperationExecutionErrorException("Error: " + e.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public UserData getUser(String userid) throws UserNotFoundException, OperationExecutionErrorException {
		SqlSession session = sessionFactory.openSession();
		UserData user = null;
		try {
			UserAndDeviceMapper mapper = session.getMapper(UserAndDeviceMapper.class);
			user = mapper.getUserBaseData(userid);
			if (user == null) {
				throw new UserNotFoundException("User not found!");
			}
			user.setRoles(mapper.getUserRoles(userid));

			/*
			 * Convert from long to date
			 */
			Collection<Long> millisecValues = mapper.readLoginAttempts(userid);
			if (millisecValues == null) {
				user.setBadLogins(new ArrayList<Date>());
			} else {
				Collection<Date> loginAttempts = new ArrayList<Date>();
				for (long value : millisecValues) {
					loginAttempts.add(new Date(value));
				}
				user.setBadLogins(loginAttempts);
			}

			user.setDevices(new ArrayList<DeviceData>());

		} catch (Exception e) {
			if (e instanceof UserNotFoundException) {
				logger.error("User not exists with id: " + userid + "  Error: " + e.getMessage());
				throw new UserNotFoundException("User not found by userid: " + userid);
			}
			logger.error("Error: " + e.getMessage());
			throw new OperationExecutionErrorException("Error: " + e.getMessage());
		} finally {
			session.close();
		}
		return user;
	}

	@Override
	public Collection<UserData> getAllUsers() throws OperationExecutionErrorException {
		SqlSession session = sessionFactory.openSession();
		Collection<UserData> users = new ArrayList<UserData>();
		try {
			UserAndDeviceMapper mapper = session.getMapper(UserAndDeviceMapper.class);
			users = mapper.getAllUsersBaseData();
			for (UserData user : users) {
				user.setRoles(mapper.getUserRoles(user.getUserid()));
				/*
				 * Convert from long to date
				 */
				Collection<Long> millisecValues = mapper.readLoginAttempts(user.getUserid());
				if (millisecValues == null) {
					user.setBadLogins(new ArrayList<Date>());
				} else {
					Collection<Date> loginAttempts = new ArrayList<Date>();
					for (long value : millisecValues) {
						loginAttempts.add(new Date(value));
					}
					user.setBadLogins(loginAttempts);
				}

				user.setDevices(new ArrayList<DeviceData>());
			}
		} catch (Exception e) {
			logger.error("getAllUsers error, message:" + e.getMessage());
			throw new OperationExecutionErrorException(e.getMessage());
		} finally {
			session.close();
		}
		return users;
	}

	@Override
	public void updateUser(UserData user) throws UserNotFoundException, OperationExecutionErrorException {
		SqlSession session = sessionFactory.openSession();
		try {
			UserAndDeviceMapper mapper = session.getMapper(UserAndDeviceMapper.class);
			int values = mapper.updateUserBase(user);
			if (values == 0) {
				throw new UserNotFoundException("User not found!");
			}
			mapper.eraseUserRoles(user.getUserid());
			mapper.createUserRoles(user);

			mapper.deleteLoginAttempts(user.getUserid(), new Date());
			if (user.getBadLogins().size() != 0) {

				// mapper.storeLoginAttempts(user);
				Collection<Double> coll = new ArrayList<Double>();
				for (Date i : user.getBadLogins()) {
					coll.add(i.getTime() * 0.001);
				}
				mapper.storeLoginAttemptsWithMilliseconds(user.getUserid(), coll);
			}
			session.commit();
		} catch (UserNotFoundException e) {
			String userid = "NULL";
			if (user != null) {
				userid = user.getUserid();
			}
			logger.error("User has not found with this id: " + userid + "  Error: " + e.getMessage());
			throw new UserNotFoundException("This user is not exists: " + userid);
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			throw new OperationExecutionErrorException("Error: " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void deleteUser(String userid) throws UserNotFoundException, OperationExecutionErrorException {
		SqlSession session = sessionFactory.openSession();
		int deletedUsers = 0;
		try {
			UserAndDeviceMapper mapper = session.getMapper(UserAndDeviceMapper.class);
			deletedUsers = mapper.deleteUser(userid);
			if (deletedUsers == 0) {
				throw new UserNotFoundException("User not found!");
			}
			session.commit();
		} catch (UserNotFoundException e) {
			logger.error("User has not with this id: " + userid + "  Error: " + e.getMessage());
			throw new UserNotFoundException("Error: User cannot be deleted because not found, userid: " + userid);
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			throw new OperationExecutionErrorException("Error: " + e.getMessage());
		} finally {
			session.close();
		}
	}

	/*
	 * DEVICE CRUD
	 */

	@Override
	public void storeDevice(DeviceData device, UserData user)
			throws DeviceAlreadyExistsException, OperationExecutionErrorException {
		SqlSession session = sessionFactory.openSession();
		try {
			UserAndDeviceMapper mapper = session.getMapper(UserAndDeviceMapper.class);
			mapper.storeDevice(device, user);
			session.commit();
		} catch (Exception e) {
			if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
				String deviceid = "NULL";
				if (device != null) {
					deviceid = device.getDeviceid();
				}
				logger.error("Duplacated device error, deviceid: " + deviceid);
				throw new DeviceAlreadyExistsException("Device already exists with this deviceid:" + deviceid);
			}
			logger.error("Error: " + e.getMessage());
			throw new OperationExecutionErrorException("Error message: " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public Collection<DeviceData> readUserDevices(UserData user) throws OperationExecutionErrorException {
		SqlSession session = sessionFactory.openSession();
		Collection<DeviceData> devices = new ArrayList<DeviceData>();
		try {
			UserAndDeviceMapper mapper = session.getMapper(UserAndDeviceMapper.class);
			devices = mapper.getUserDevices(user);
			if (devices == null) {
				devices = new ArrayList<DeviceData>();
			}
		} catch (Exception e) {
			logger.error("Error message: " + e.getMessage());
			throw new OperationExecutionErrorException("Error: " + e.getMessage());
		} finally {
			session.close();
		}
		return devices;
	}

	@Override
	public void updateDevice(DeviceData device, UserData user)
			throws DeviceNotFoundException, OperationExecutionErrorException {
		SqlSession session = sessionFactory.openSession();
		try {
			UserAndDeviceMapper mapper = session.getMapper(UserAndDeviceMapper.class);
			int updated = mapper.updateDevice(device, user);
			if (updated == 0) {
				throw new DeviceNotFoundException();
			}
			session.commit();
		} catch (Exception e) {
			if (e instanceof DeviceNotFoundException) {
				String deviceid = "NULL";
				if (device != null) {
					deviceid = device.getDeviceid();
				}
				logger.error("Device update has failed, no device has found with id: " + deviceid);
				throw new DeviceNotFoundException(
						"Device has not found with this id: " + deviceid + "  error message: " + e.getMessage());
			}
			logger.error("Error: " + e.getMessage());
			throw new OperationExecutionErrorException("Error: " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void deleteDevice(DeviceData device, UserData user)
			throws DeviceNotFoundException, OperationExecutionErrorException {
		SqlSession session = sessionFactory.openSession();
		try {
			UserAndDeviceMapper mapper = session.getMapper(UserAndDeviceMapper.class);
			int deleted = mapper.deleteDevice(device, user);
			if (deleted == 0) {
				throw new DeviceNotFoundException();
			}
			session.commit();
		} catch (Exception e) {
			if (e instanceof DeviceNotFoundException) {
				String deviceid = "NULL";
				if (device != null) {
					deviceid = device.getDeviceid();
				}
				logger.error("Device deletion has failed, no device found with this id: " + deviceid
						+ "  Error message: " + e.getMessage());
				throw new DeviceNotFoundException("Device not found with id: " + deviceid + "  error" + e.getMessage());
			}
			throw new OperationExecutionErrorException("Error: " + e.getMessage());
		} finally {
			session.close();
		}
	}

}
