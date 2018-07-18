package uni.miskolc.ips.ilona.tracking.persist.exception;

public class DatabaseProblemException extends OperationExecutionErrorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DatabaseProblemException() {
		
	}
	
	public DatabaseProblemException(String message) {
		super(message);
	}
	
	public DatabaseProblemException(Throwable cause) {
		super(cause);
	}
	
	public DatabaseProblemException(String message, Throwable cause) {
		super(message, cause);
	}

}
