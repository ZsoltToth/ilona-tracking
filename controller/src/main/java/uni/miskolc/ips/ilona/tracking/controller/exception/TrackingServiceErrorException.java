package uni.miskolc.ips.ilona.tracking.controller.exception;

public class TrackingServiceErrorException extends TrackingControllerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TrackingServiceErrorException() {
		super();
	}

	public TrackingServiceErrorException(String message) {
		super(message);
	}

	public TrackingServiceErrorException(Throwable cause) {
		super(cause);
	}

	public TrackingServiceErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
