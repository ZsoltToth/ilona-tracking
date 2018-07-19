package uni.miskolc.ips.ilona.tracking.service.exceptions;

public class DeviceNotFoundException extends DeviceServiceException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeviceNotFoundException() {
		super();
	}

	public DeviceNotFoundException(String message) {
		super(message);
	}

	public DeviceNotFoundException(Throwable cause) {
		super(cause);
	}

	public DeviceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
