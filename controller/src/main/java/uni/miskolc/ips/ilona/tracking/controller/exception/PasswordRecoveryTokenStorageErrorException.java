package uni.miskolc.ips.ilona.tracking.controller.exception;

public class PasswordRecoveryTokenStorageErrorException extends PasswordRecoveryException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordRecoveryTokenStorageErrorException() {
		super();
	}

	public PasswordRecoveryTokenStorageErrorException(String message) {
		super(message);
	}

	public PasswordRecoveryTokenStorageErrorException(Throwable cause) {
		super(cause);
	}

	public PasswordRecoveryTokenStorageErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
