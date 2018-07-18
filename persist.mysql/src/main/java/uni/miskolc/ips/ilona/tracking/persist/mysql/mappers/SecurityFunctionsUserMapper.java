package uni.miskolc.ips.ilona.tracking.persist.mysql.mappers;

import java.util.Collection;
import java.util.Date;

import org.apache.ibatis.annotations.Param;

import uni.miskolc.ips.ilona.tracking.persist.mysql.model.PasswordRecoveryTokenMapper;

public interface SecurityFunctionsUserMapper {

	int updatePassword(@Param(value = "userid") String userid, @Param(value = "password") String password);

	int updateEnabled(@Param(value = "userid") String userid, @Param(value = "enabled") boolean enabled);

	int updateAccountExpiration(@Param(value = "userid") String userid,
			@Param(value = "accountExpiration") Date accountExpiration);

	int eraseRoles(@Param(value = "userid") String userid);

	int insertRoles(@Param(value = "userid") String userid, @Param(value = "roles") Collection<String> roles);

	int eraseBadLogins(@Param(value = "userid") String userid);

	int insertBadLogins(@Param(value = "userid") String userid,
			@Param(value = "badLogins") Collection<Double> badLogins);

	int updateLockedAndUntilLocked(@Param(value = "userid") String userid,
			@Param(value = "nonlocked") boolean nonlocked, @Param(value = "lockedUntil") Double lockedUntil);

	int erasePasswordToken(@Param(value = "userid") String userid);

	int storePasswordToken(@Param(value = "userid") String userid, @Param(value = "token") String token,
			@Param(value = "validuntil") double validUntil);

	PasswordRecoveryTokenMapper readPasswordToken(@Param(value = "userid") String userid);
}
