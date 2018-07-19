package uni.miskolc.ips.ilona.tracking.persist.exception;

public class UserDAOException extends TrackingDAOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserDAOException() {
		super();
	}

	public UserDAOException(String message) {
		super(message);
	}

	public UserDAOException(Throwable cause) {
		super(cause);
	}

	public UserDAOException(String message, Throwable cause) {
		super(message, cause);
	}

}
