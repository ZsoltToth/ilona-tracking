package uni.miskolc.ips.ilona.tracking.controller.exception;

import uni.miskolc.ips.ilona.tracking.util.validate.ValidityStatusHolder;

public class UserChangeDetailsExecutionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ValidityStatusHolder validityErrors;

	public UserChangeDetailsExecutionException() {
		super();
	}

	public UserChangeDetailsExecutionException(String message) {
		super(message);
	}

	public UserChangeDetailsExecutionException(Throwable cause) {
		super(cause);
	}

	public UserChangeDetailsExecutionException(String message, ValidityStatusHolder validityErrors) {
		super(message);
		this.validityErrors = validityErrors;
	}

	public UserChangeDetailsExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserChangeDetailsExecutionException(String message, Throwable cause, ValidityStatusHolder validityErrors) {
		super(message, cause);
		this.validityErrors = validityErrors;
	}

	public ValidityStatusHolder getValidityErrors() {
		return validityErrors;
	}
}
