package cs355.model.exception;

public class InvalidHandleException extends InvalidModelException {
    public InvalidHandleException() {
        super();
    }

    public InvalidHandleException(String format) {
        super(format);
    }
}
