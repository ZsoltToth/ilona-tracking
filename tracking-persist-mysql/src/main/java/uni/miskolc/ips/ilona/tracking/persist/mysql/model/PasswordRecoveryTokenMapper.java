package uni.miskolc.ips.ilona.tracking.persist.mysql.model;

/**
 * 
 * @author Patrik / A5USL0
 *
 */
public class PasswordRecoveryTokenMapper {

	private String userid;

	private String token;

	private long validUntil;

	public PasswordRecoveryTokenMapper() {

	}

	public PasswordRecoveryTokenMapper(String userid, String token, long validUntil) {
		super();
		this.userid = userid;
		this.token = token;
		this.validUntil = validUntil;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(long validUntil) {
		this.validUntil = validUntil;
	}

}
