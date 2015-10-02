package cs355.model.exception;

/**
 * Created by BaronVonBaerenstein on 10/1/2015.
 */
public class InvalidModelException extends Exception {
    public InvalidModelException() {
        super();
    }

    public InvalidModelException(String format) {
            super(format);
        }
}

