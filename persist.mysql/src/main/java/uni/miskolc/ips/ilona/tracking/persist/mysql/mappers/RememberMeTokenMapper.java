package uni.miskolc.ips.ilona.tracking.persist.mysql.mappers;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import uni.miskolc.ips.ilona.tracking.persist.mysql.model.PersistenceTokenMapper;

public interface RememberMeTokenMapper {

	public void createNewToken(@Param(value = "token") PersistentRememberMeToken token);

	public PersistenceTokenMapper getTokenForSeries(@Param(value = "series") String series);

	public void removeUserTokens(@Param(value = "userid") String userid);

	public void updateToken(@Param(value = "series") String series, @Param(value = "tokenValue") String tokenValue,
			@Param(value = "lastUsed") Date lastUsed);
}
