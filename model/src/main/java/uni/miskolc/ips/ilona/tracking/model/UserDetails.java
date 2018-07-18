package uni.miskolc.ips.ilona.tracking.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class stores the information about the user. It stores every details
 * 
 * @author Patrik
 *
 */
public class UserDetails implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The user identification string. This is the primary key in the table.
	 * Must be different for each user.
	 * 
	 * PATTERN: MAXIMUM 10 characters long.
	 */
	private String userid;

	/**
	 * This is an arbitrary username, this can be changed freely.<br>
	 * <b>PATTERN:</b>
	 */
	private String username;

	/**
	 * 
	 */
	private String password;

	/**
	 * 
	 */
	private String email;

	/**
	 * 
	 */
	private boolean enabled;

	/**
	 * 
	 */
	private boolean accountNonExpired;

	/**
	 * 
	 */
	private boolean accountNonLocked;

	/**
	 * 
	 */
	private boolean credentialsNonExpired;

	/**
	 * Parameterless constructor for serialization, Spring IoC or others.
	 */
	
	private Collection<String> roles;
	
	public UserDetails() {
		roles = new ArrayList<String>();
	}

	/**
	 * 
	 * @param userid
	 * @param username
	 * @param password
	 * @param email
	 * @param enabled
	 * @param accountNonExpired
	 * @param accountNonLocked
	 * @param credentialsNonExpired
	 */
	public UserDetails(String userid, String username, String password, String email, boolean enabled,
			boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, Collection<String> roles) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.roles = roles;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Collection<String> getRoles() {
		return roles;
	}

	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}

	/**
	 * Create a shallow copy(byte to byte copy) from the current object.
	 * 
	 * @return The shallow copied verison of the current object.
	 */
	public UserDetails createShallowCopy() {
		try {
			return (UserDetails) this.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}

	}

	/**
	 * Create a deep copy from the current object. This will serialize and
	 * deserialize the current object to make a completely new version from
	 * it.<br>
	 * 
	 * @return A total new copy of the current object. (except static of
	 *         course)<br>
	 *         if an error occurs, the return value will be null!
	 */
	public UserDetails createDeepCopy() {

		ByteArrayOutputStream bos = null;
		ObjectOutputStream out = null;
		ObjectInputStream ois = null;
		ByteArrayInputStream bis = null;
		UserDetails userdetails = null;

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
			userdetails = (UserDetails) ois.readObject();

		} catch (Exception e) {
			userdetails = null;
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

		return userdetails;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accountNonExpired ? 1231 : 1237);
		result = prime * result + (accountNonLocked ? 1231 : 1237);
		result = prime * result + (credentialsNonExpired ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		UserDetails other = (UserDetails) obj;
		if (accountNonExpired != other.accountNonExpired)
			return false;
		if (accountNonLocked != other.accountNonLocked)
			return false;
		if (credentialsNonExpired != other.credentialsNonExpired)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enabled != other.enabled)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDetails [userid=" + userid + ", username=" + username + ", email=" + email + ", enabled=" + enabled
				+ ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired + "]";
	}
	
	
}
