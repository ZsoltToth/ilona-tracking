package uni.miskolc.ips.ilona.tracking.persist.exception;

public class DeviceAlreadyExistsException extends DeviceDAOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DeviceAlreadyExistsException() {
		super();
	}
	
	public DeviceAlreadyExistsException(String message) {
		super(message);
	}
	
	public DeviceAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	
	public DeviceAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}
