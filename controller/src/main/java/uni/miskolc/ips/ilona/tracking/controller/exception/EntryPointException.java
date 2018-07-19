package uni.miskolc.ips.ilona.tracking.controller.exception;

public class EntryPointException extends TrackingControllerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntryPointException() {
		super();
	}

	public EntryPointException(String message) {
		super(message);
	}

	public EntryPointException(Throwable cause) {
		super(cause);
	}

	public EntryPointException(String message, Throwable cause) {
		super(message, cause);
	}
}