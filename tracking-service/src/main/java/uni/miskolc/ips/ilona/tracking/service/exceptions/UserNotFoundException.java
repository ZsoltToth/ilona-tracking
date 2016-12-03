package uni.miskolc.ips.ilona.tracking.service.exceptions;

public class UserNotFoundException extends UserServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
