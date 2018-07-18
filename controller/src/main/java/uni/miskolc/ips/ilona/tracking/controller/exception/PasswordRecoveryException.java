package uni.miskolc.ips.ilona.tracking.controller.exception;

public class PasswordRecoveryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordRecoveryException() {
		super();
	}

	public PasswordRecoveryException(String message) {
		super(message);
	}

	public PasswordRecoveryException(Throwable cause) {
		super(cause);
	}

	public PasswordRecoveryException(String message, Throwable cause) {
		super(message, cause);
	}
}
