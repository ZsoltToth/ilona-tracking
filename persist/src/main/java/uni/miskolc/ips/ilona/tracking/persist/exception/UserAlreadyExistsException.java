package uni.miskolc.ips.ilona.tracking.persist.exception;

public class UserAlreadyExistsException extends UserDAOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9204254472889191153L;

	public UserAlreadyExistsException() {
		
	}
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}
	
	public UserAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	
	public UserAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}
