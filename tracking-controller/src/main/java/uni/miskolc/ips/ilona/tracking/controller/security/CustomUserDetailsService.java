package uni.miskolc.ips.ilona.tracking.controller.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import uni.miskolc.ips.ilona.tracking.controller.model.UserSecurityDetails;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.persist.UserAndDeviceDAO;
import uni.miskolc.ips.ilona.tracking.persist.exception.OperationExecutionErrorException;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserAndDeviceDAO userDeviceDAO;

	public CustomUserDetailsService() {

	}

	public CustomUserDetailsService(UserAndDeviceDAO userdao) {
		this.userDeviceDAO = userdao;
	}

	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		UserData userDB = null;

		try {
			userDB = userDeviceDAO.getUser(userid);
			return this.createUserDetails(userDB);

		} catch (UserNotFoundException e) {

			throw new UsernameNotFoundException("User is not found by this id: " + userid);
		} catch (OperationExecutionErrorException e) {

			throw new UsernameNotFoundException("User is not found by this id: " + userid);
		} finally {

		}
	}

	public void setUserDeviceDAO(UserAndDeviceDAO userDeviceDAO) {
		this.userDeviceDAO = userDeviceDAO;
	}

	private UserSecurityDetails createUserDetails(UserData userdata) {
		UserSecurityDetails userDetails = new UserSecurityDetails();
		try {
			userDetails.setUserid(userdata.getUserid());
			userDetails.setUsername(userdata.getUsername());
			userDetails.setEmail(userdata.getEmail());
			userDetails.setPassword(userdata.getPassword());
			userDetails.setEnabled(userdata.isEnabled());
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for (String role : userdata.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role));
			}
			userDetails.setAuthorities(authorities);
			userDetails.setLastLoginDate(userdata.getLastLoginDate());
			userDetails.setCredentialNonExpiredUntil(userdata.getCredentialNonExpiredUntil());
			userDetails.setLockedUntil(userdata.getLockedUntil());
			userDetails.setNonLocked(userdata.isNonLocked());
			userDetails.setBadLogins(userdata.getBadLogins());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return userDetails;
	}

}
