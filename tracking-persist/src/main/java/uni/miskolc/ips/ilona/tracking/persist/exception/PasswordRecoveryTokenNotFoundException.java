package uni.miskolc.ips.ilona.tracking.persist.exception;

public class PasswordRecoveryTokenNotFoundException extends PasswordRecoveryException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordRecoveryTokenNotFoundException() {

	}

	public PasswordRecoveryTokenNotFoundException(String message) {
		super(message);
	}

	public PasswordRecoveryTokenNotFoundException(Throwable cause) {
		super(cause);
	}

	public PasswordRecoveryTokenNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
