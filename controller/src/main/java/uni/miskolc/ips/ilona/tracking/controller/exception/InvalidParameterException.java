package uni.miskolc.ips.ilona.tracking.controller.exception;

public class InvalidParameterException extends TrackingControllerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidParameterException() {
		super();
	}

	public InvalidParameterException(String message) {
		super(message);
	}

	public InvalidParameterException(Throwable cause) {
		super(cause);
	}

	public InvalidParameterException(String message, Throwable cause) {
		super(message, cause);
	}
}
