package uni.miskolc.ips.ilona.tracking.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 
 * @author Patrik
 *
 */
public class PasswordRecoveryToken {

	private String userid;

	private String token;

	private Date tokenValidUntil;

	public PasswordRecoveryToken() {

	}

	public PasswordRecoveryToken(String userid, String token, Date tokenValidUntil) {
		super();
		this.userid = userid;
		this.token = token;
		this.tokenValidUntil = tokenValidUntil;
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

	public Date getTokenValidUntil() {
		return tokenValidUntil;
	}

	public void setTokenValidUntil(Date tokenValidUntil) {
		this.tokenValidUntil = tokenValidUntil;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((tokenValidUntil == null) ? 0 : tokenValidUntil.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordRecoveryToken other = (PasswordRecoveryToken) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (tokenValidUntil == null) {
			if (other.tokenValidUntil != null)
				return false;
		} else if (!tokenValidUntil.equals(other.tokenValidUntil))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}

	public PasswordRecoveryToken shallowCopy() {
		try {
			return (PasswordRecoveryToken) this.clone();
		} catch (Exception e) {
			return null;
		}
	}

	public PasswordRecoveryToken deepCopy() {
		ByteArrayOutputStream bos = null;
		ObjectOutputStream out = null;
		ObjectInputStream ois = null;
		ByteArrayInputStream bis = null;
		PasswordRecoveryToken passwordToken = null;

		try {
			/*
			 * Serialization part.
			 */
			bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			out.writeObject(this);
			out.flush();
			// out.close();

			/*
			 * Deserialization part.
			 */
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			passwordToken = (PasswordRecoveryToken) ois.readObject();

		} catch (Exception e) {
			passwordToken = null;

		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception a) {

				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (Exception a) {

				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (Exception a) {

				}
			}

			if (bis != null) {
				try {
					bis.close();
				} catch (Exception a) {

				}
			}
		}

		return passwordToken;
	}

	@Override
	public String toString() {
		return "PasswordRecoveryToken [userid=" + userid + ", token=" + token + ", tokenValidUntil=" + tokenValidUntil
				+ "]";
	}
}
