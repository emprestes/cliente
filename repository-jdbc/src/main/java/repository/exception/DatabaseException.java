package repository.exception;

public class DatabaseException extends Exception {

    private static final long serialVersionUID = -3441942591814520080L;

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
