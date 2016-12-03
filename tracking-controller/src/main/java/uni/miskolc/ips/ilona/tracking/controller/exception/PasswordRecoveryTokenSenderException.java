package uni.miskolc.ips.ilona.tracking.controller.exception;

public class PasswordRecoveryTokenSenderException extends PasswordRecoveryException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordRecoveryTokenSenderException() {
		super();
	}

	public PasswordRecoveryTokenSenderException(String message) {
		super(message);
	}

	public PasswordRecoveryTokenSenderException(Throwable cause) {
		super(cause);
	}

	public PasswordRecoveryTokenSenderException(String message, Throwable cause) {
		super(message, cause);
	}
}
