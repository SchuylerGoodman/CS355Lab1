package cs355.view;

import cs355.model.drawing.*;
import cs355.model.drawing.exception.InvalidHandleException;
import cs355.model.drawing.exception.InvalidShapeException;
import cs355.model.drawing.selectable.CircleHandle;
import cs355.model.drawing.selectable.Handle;

/**
 * Created by goodman on 9/9/2015.
 */
public class DrawableFactory {
    public IDrawable create(Shape s) throws InvalidShapeException {
        if (s instanceof Line) {
            Line line = (Line) s;
            return new LineDrawable(line);
        }
        else if (s instanceof Square) {
            Square square = (Square) s;
            return new SquareDrawable(square);
        }
        else if (s instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) s;
            return new RectangleDrawable(rectangle);
        }
        else if (s instanceof Circle) {
            Circle circle = (Circle) s;
            return new CircleDrawable(circle);
        }
        else if (s instanceof Ellipse) {
            Ellipse ellipse = (Ellipse) s;
            return new EllipseDrawable(ellipse);
        }
        else if (s instanceof Triangle) {
            Triangle triangle = (Triangle) s;
            return new TriangleDrawable(triangle);
        }
        else {
            throw new InvalidShapeException(
                    String.format(
                            "Invalid Shape \"%s\" given for drawable creation",
                            s.getClass().getName()
                    )
            );
        }
    }

    /**
     * Creates an IDrawable for a handle.
     *
     * @param h = the model for the handle to draw.
     * @param s = the shape the handle is used to manipulate.
     * @return an IDrawable for the given handle
     * @throws InvalidHandleException if the handle is not supported by the factory.
     */
    public IDrawable create(Handle h, Shape s) throws InvalidHandleException {
        if (h instanceof CircleHandle) {
            CircleHandle circleHandle = (CircleHandle) h;
            return new CircleHandleDrawable(s, circleHandle);
        }
        else {
            throw new InvalidHandleException(
                    String.format(
                            "Invalid selectable \"%s\" given for drawable creation",
                            h.getClass().getName()
                    )
            );
        }
    }
}
