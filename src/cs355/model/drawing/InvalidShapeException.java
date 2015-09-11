package cs355.model.drawing;

/**
 * Created by goodman on 9/10/2015.
 */
public class InvalidShapeException extends Exception {
    public InvalidShapeException() {
        super();
    }

    public InvalidShapeException(String format) {
        super(format);
    }
}
