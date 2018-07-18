package uni.miskolc.ips.ilona.tracking.controller.model;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSecurityDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userid;

	private String username;

	private String email;

	private String password;

	private boolean enabled;

	private Collection<? extends GrantedAuthority> authorities;

	private Date lastLoginDate;

	private Date credentialNonExpiredUntil;

	private Date lockedUntil;

	private boolean nonLocked;

	private Collection<Date> badLogins;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// one year: 31536000000
		if ((new Date().getTime() - lastLoginDate.getTime()) > 31536000000L) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return this.nonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		if (new Date().getTime() > this.credentialNonExpiredUntil.getTime()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isEnabled() {

		return enabled;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public void setBadLogins(Collection<Date> badLogins) {
		this.badLogins = badLogins;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

}
