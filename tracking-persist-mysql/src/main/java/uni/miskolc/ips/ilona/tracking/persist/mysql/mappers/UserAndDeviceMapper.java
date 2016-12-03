package uni.miskolc.ips.ilona.tracking.persist.mysql.mappers;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import uni.miskolc.ips.ilona.tracking.model.DeviceData;
import uni.miskolc.ips.ilona.tracking.model.UserData;

public interface UserAndDeviceMapper {

	/*
	 * USER PART
	 */

	/**
	 * UserBase part This basedata contains the following columns:
	 * 
	 * - userid - username - email - password - enabled - nonlocked -
	 * lockeduntil - lastlogindate - credentialsvaliduntil
	 * 
	 * and fills these values into a UserBase.
	 * 
	 */
	int createUserBaseData(UserData user);

	UserData getUserBaseData(String userid);

	Collection<UserData> getAllUsersBaseData();

	int updateUserBase(UserData user);

	int deleteUser(String userid);

	/*
	 * UserRole part
	 */
	Collection<String> getUserRoles(String userid);

	Map<String, String> getAllUsersRoles();

	int eraseUserRoles(String userid);

	int createUserRoles(UserData user);

	/*
	 * LoginAttempts part
	 */

	Collection<Long> readLoginAttempts(String userid);

	int deleteLoginAttempts(@Param(value = "userid") String userid, @Param(value = "beforeDate") Date beforeDate);

	int storeLoginAttemptsWithMilliseconds(@Param(value = "userid") String userid,
			@Param(value = "attempts") Collection<Double> attempts);

	int storeLoginAttempts(UserData user);

	/*
	 * DEVICE PART
	 */

	int storeDevice(@Param(value = "device") DeviceData device, @Param(value = "user") UserData user);

	Collection<DeviceData> getUserDevices(@Param("user") UserData user);

	int deleteDevice(@Param(value = "device") DeviceData device, @Param(value = "user") UserData user);

	int updateDevice(@Param(value = "device") DeviceData device, @Param(value = "user") UserData user);

}
