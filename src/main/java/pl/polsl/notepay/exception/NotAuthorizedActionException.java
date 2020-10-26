package pl.polsl.notepay.exception;

public class NotAuthorizedActionException extends RuntimeException {

    public NotAuthorizedActionException(String message) {
        super(message);
    }

}
