package uni.miskolc.ips.ilona.tracking.persist.exception;

public class PasswordRecoveryException extends TrackingDAOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PasswordRecoveryException() {
		
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
