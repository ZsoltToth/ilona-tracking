package uni.miskolc.ips.ilona.tracking.controller.exception;

import org.springframework.security.core.AuthenticationException;

public class AccountIsNotEnabledException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountIsNotEnabledException(String message) {
		super(message);
	}

	public AccountIsNotEnabledException(String message, Throwable cause) {
		super(message, cause);
	}

}
