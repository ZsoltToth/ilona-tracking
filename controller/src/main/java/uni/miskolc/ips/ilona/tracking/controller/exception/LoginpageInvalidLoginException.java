package uni.miskolc.ips.ilona.tracking.controller.exception;

import uni.miskolc.ips.ilona.tracking.util.validate.ValidityStatusHolder;

public class LoginpageInvalidLoginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ValidityStatusHolder statusHolder;
	
	public LoginpageInvalidLoginException() {
		
	}
	
	public LoginpageInvalidLoginException(String errorMessage) {
		super(errorMessage);
	}
	
	public LoginpageInvalidLoginException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
	
	public LoginpageInvalidLoginException(String errorMessage, Throwable error, ValidityStatusHolder statusHolder) {
		super(errorMessage, error);
		this.statusHolder = statusHolder;
	}
	
	public LoginpageInvalidLoginException(ValidityStatusHolder statusHolder) {
		this.statusHolder = statusHolder;
	}
	
	public LoginpageInvalidLoginException(String errorMessage, ValidityStatusHolder statusHolder) {
		super(errorMessage);
		this.statusHolder = statusHolder;
	}

	public ValidityStatusHolder getStatusHolder() {
		return statusHolder;
	}
	
}
