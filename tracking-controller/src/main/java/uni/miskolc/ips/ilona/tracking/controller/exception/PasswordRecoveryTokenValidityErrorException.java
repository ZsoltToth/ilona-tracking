package uni.miskolc.ips.ilona.tracking.controller.exception;

public class PasswordRecoveryTokenValidityErrorException extends PasswordRecoveryException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordRecoveryTokenValidityErrorException() {
		super();
	}

	public PasswordRecoveryTokenValidityErrorException(String message) {
		super(message);
	}

	public PasswordRecoveryTokenValidityErrorException(Throwable cause) {
		super(cause);
	}

	public PasswordRecoveryTokenValidityErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
