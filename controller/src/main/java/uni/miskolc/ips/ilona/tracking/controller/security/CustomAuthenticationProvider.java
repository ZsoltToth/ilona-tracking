package uni.miskolc.ips.ilona.tracking.controller.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import uni.miskolc.ips.ilona.tracking.controller.exception.AccountIsNotEnabledException;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	private UserDetailsService userService;

	private PasswordEncoder passwordEncoder;

	public CustomAuthenticationProvider() {

	}

	public CustomAuthenticationProvider(UserDetailsService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		/*
		 * if
		 * (!auth.getClass().equals(UsernamePasswordAuthenticationToken.class))
		 * { return null; }
		 */

		String userid = auth.getPrincipal().toString();
		String password = auth.getCredentials().toString();

		if (userid == null || password == null) {
			throw new BadCredentialsException("Invalid username or password!");
		}

		if (userid.equals("") || password.equals("")) {
			throw new BadCredentialsException("Invalid username or password!");
		}
		UserDetails user = null;

		try {
			user = userService.loadUserByUsername(userid);
			if (user.isEnabled() == false) {
				throw new AccountIsNotEnabledException("Account is not enabled: " + user.getUsername());
			}

		} catch (UsernameNotFoundException e) {
			return null;
		}
		if (user == null) {
			return null;
		}

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("");
		}

		Authentication successAuth = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
				user.getAuthorities());
		return successAuth;

	}

	@Override
	public boolean supports(Class<?> arg0) {
		if (arg0.equals(UsernamePasswordAuthenticationToken.class)) {
			return true;
		}
		return false;
	}
}
