package uni.miskolc.ips.ilona.tracking.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.xml.soap.Node;

/**
 * Dokumentáció készítése!
 * 
 * @author Patrik / A5USL0
 *
 */
public class UserData implements Comparable<UserData>, Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * User base fields.
	 */
	/**
	 * A unique id for the the current user.
	 */
	private String userid;

	/**
	 * An arbitrary username.
	 */
	private String username;

	/**
	 * This field is used for password recovery,
	 */
	private String email;

	/**
	 * A hashed password.
	 */
	private String password;

	/*
	 * Security fields.
	 */

	/**
	 * 
	 */
	private boolean enabled;

	/**
	 * ROLE_USER, ROLE_ADMIN stb.
	 */
	private Collection<String> roles;

	/**
	 * 
	 */
	private Date lastLoginDate;

	/**
	 * 
	 */
	private Date credentialNonExpiredUntil;

	/**
	 * If the nonLocked is false, the account is locked until this date. The
	 * length of the lock is defined in the TracingCentralManager class.
	 */
	private Date lockedUntil;

	/**
	 * 
	 */
	private boolean nonLocked;

	/**
	 * Hibás bejelentkezések
	 */
	private Collection<Date> badLogins;

	/**
	 * Eszközök az adott felhasználóhoz.
	 */
	private Collection<DeviceData> devices;

	public UserData() {

	}

	public UserData(String userid, String username, String email, String password, boolean enabled,
			Collection<String> roles, Date lastLoginDate, Date credentialNonExpiredUntil, Date lockedUntil,
			boolean nonLocked, Collection<Date> badLogins, Collection<DeviceData> devices) {
		super();
		this.userid = userid;
		this.username = username;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
		this.lastLoginDate = lastLoginDate;
		this.credentialNonExpiredUntil = credentialNonExpiredUntil;
		this.lockedUntil = lockedUntil;
		this.nonLocked = nonLocked;
		this.badLogins = badLogins;
		this.devices = devices;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<String> getRoles() {
		return roles;
	}

	public Collection<String> setRoles(Collection<String> roles) {
		if (roles != null) {
			this.roles = roles;
			return roles;
		}
		return null;
	}

	public String addRole(String role) {
		if (role == null) {
			return null;
		}
		boolean exists = false;
		for (String storedrole : roles) {
			if (storedrole.equals(role)) {
				exists = true;
			}
		}
		if (exists != true) {
			roles.add(role);
		}
		return role;
	}

	public String removeRole(String role) {
		if (role == null) {
			return null;
		}
		roles.remove(role);
		return role;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getCredentialNonExpiredUntil() {
		return credentialNonExpiredUntil;
	}

	public void setCredentialNonExpiredUntil(Date credentialNonExpiredUntil) {
		this.credentialNonExpiredUntil = credentialNonExpiredUntil;
	}

	public Date getLockedUntil() {
		return lockedUntil;
	}

	public void setLockedUntil(Date lockedUntil) {
		this.lockedUntil = lockedUntil;
	}

	public boolean isNonLocked() {
		return nonLocked;
	}

	public void setNonLocked(boolean nonLocked) {
		this.nonLocked = nonLocked;
	}

	public Collection<Date> getBadLogins() {
		return badLogins;
	}

	public Collection<Date> setBadLogins(Collection<Date> badLogins) {
		if (badLogins != null) {
			this.badLogins = badLogins;
			return badLogins;
		}
		return null;
	}

	public Date addBadLogin(Date login) {
		if (login == null) {
			return null;
		}
		boolean exists = false;
		for (Date storedLogin : this.badLogins) {
			if (storedLogin.getTime() == login.getTime()) {
				exists = true;
			}
		}
		if (exists != true) {
			this.badLogins.add(login);
		}
		return login;
	}

	public Date removeBadLogin(Date login) {
		if (login == null) {
			return null;
		}
		this.badLogins.remove(login);
		return login;
	}

	public Collection<DeviceData> getDevices() {
		return devices;
	}

	public Collection<DeviceData> setDevices(Collection<DeviceData> devices) {
		if (devices != null) {
			this.devices = devices;
			return devices;
		}
		return null;
	}

	public DeviceData addDevice(DeviceData newDevice) {
		if (newDevice == null) {
			return null;
		}
		/*
		 * Update
		 */
		for (DeviceData dev : this.devices) {
			if (dev.getDeviceid().equals(newDevice.getDeviceid())) {
				dev.setDeviceName(newDevice.getDeviceName());
				dev.setDeviceType(newDevice.getDeviceType());
				dev.setDeviceTypeName(newDevice.getDeviceTypeName());
				return newDevice;
			}
		}
		/*
		 * Not exists, add
		 */
		this.devices.add(newDevice);
		return newDevice;
	}

	public DeviceData removeDevice(DeviceData noDevice) {
		if (noDevice == null) {
			return null;
		}
		
		Collection<DeviceData> removableDevs = new ArrayList<DeviceData>();
		String devid = noDevice.getDeviceid();
		for(DeviceData dev : devices) {
			if(dev.getDeviceid().equals(devid)) {
				removableDevs.add(dev);
			}
		}
		devices.removeAll(removableDevs);
		return noDevice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((badLogins == null) ? 0 : badLogins.hashCode());
		result = prime * result + ((credentialNonExpiredUntil == null) ? 0 : credentialNonExpiredUntil.hashCode());
		result = prime * result + ((devices == null) ? 0 : devices.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((lastLoginDate == null) ? 0 : lastLoginDate.hashCode());
		result = prime * result + ((lockedUntil == null) ? 0 : lockedUntil.hashCode());
		result = prime * result + (nonLocked ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
		UserData other = (UserData) obj;
		if (badLogins == null) {
			if (other.badLogins != null)
				return false;
		} else if (!badLogins.equals(other.badLogins))
			return false;
		if (credentialNonExpiredUntil == null) {
			if (other.credentialNonExpiredUntil != null)
				return false;
		} else if (!credentialNonExpiredUntil.equals(other.credentialNonExpiredUntil))
			return false;
		if (devices == null) {
			if (other.devices != null)
				return false;
		} else if (!devices.equals(other.devices))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enabled != other.enabled)
			return false;
		if (lastLoginDate == null) {
			if (other.lastLoginDate != null)
				return false;
		} else if (!lastLoginDate.equals(other.lastLoginDate))
			return false;
		if (lockedUntil == null) {
			if (other.lockedUntil != null)
				return false;
		} else if (!lockedUntil.equals(other.lockedUntil))
			return false;
		if (nonLocked != other.nonLocked)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
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

	/**
	 * 
	 * @return
	 */
	public UserData shallowCopy() {
		try {
			return (UserData) this.clone();
		} catch (Exception e) {
			return null;
		}
	}

	public UserData deepCopy() {
		ByteArrayOutputStream bos = null;
		ObjectOutputStream out = null;
		ObjectInputStream ois = null;
		ByteArrayInputStream bis = null;
		UserData userData = null;

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
			userData = (UserData) ois.readObject();

		} catch (Exception e) {
			userData = null;

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

		return userData;
	}

	@Override
	public String toString() {
		return "UserData [userid=" + userid + ", username=" + username + ", email=" + email + ", password="
				+ "[PROTECTED]" + ", enabled=" + enabled + ", roles=" + roles + ", lastLoginDate=" + lastLoginDate
				+ ", credentialNonExpiredUntil=" + credentialNonExpiredUntil + ", lockedUntil=" + lockedUntil
				+ ", nonLocked=" + nonLocked + ", badLogins=" + badLogins + ", devices=" + devices + "]";
	}

	@Override
	public int compareTo(UserData o) {
		if (o == null) {
			return 1;
		}

		if (!(o instanceof UserData)) {
			return 1;
		}
		return this.userid.compareTo(o.userid);
	}

}
