package cs355.model.drawing.exception;

/**
 * Created by goodman on 9/10/2015.
 */
public class InvalidShapeException extends InvalidModelException {
    public InvalidShapeException() {
        super();
    }

    public InvalidShapeException(String format) {
        super(format);
    }
}
