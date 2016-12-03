package uni.miskolc.ips.ilona.tracking.service.exceptions;

public class TrackingServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TrackingServiceException() {
		super();
	}
	
	public TrackingServiceException(String message) {
		super(message);
	}
	
	public TrackingServiceException(Throwable cause) {
		super(cause);
	}
	
	public TrackingServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
