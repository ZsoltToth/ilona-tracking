package uni.miskolc.ips.ilona.tracking.persist.exception;

public class UserNotFoundInDatabaseException extends UserDAOException {

    /**
     *
     */
    private static final long serialVersionUID = -712157348319823447L;

    public UserNotFoundInDatabaseException() {

    }

    public UserNotFoundInDatabaseException(String message) {
        super(message);
    }

    public UserNotFoundInDatabaseException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundInDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
