package uni.miskolc.ips.ilona.tracking.controller.exception;

public class PasswordRecoveryTokenRestoreErrorException extends PasswordRecoveryException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordRecoveryTokenRestoreErrorException() {
		super();
	}

	public PasswordRecoveryTokenRestoreErrorException(String message) {
		super(message);
	}

	public PasswordRecoveryTokenRestoreErrorException(Throwable cause) {
		super(cause);
	}

	public PasswordRecoveryTokenRestoreErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
