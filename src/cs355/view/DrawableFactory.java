package cs355.view;

import cs355.model.drawing.*;
import cs355.model.exception.InvalidHandleException;
import cs355.model.exception.InvalidShapeException;
import cs355.model.drawing.selectable.CircleHandle;
import cs355.model.drawing.selectable.Handle;

/**
 * Created by goodman on 9/9/2015.
 */
public class DrawableFactory {
    public IDrawable create(Shape s) throws InvalidShapeException {
        IDrawable drawable;
        if (s instanceof Line) {
            Line line = (Line) s;
            drawable = new LineDrawable(line);
        }
        else if (s instanceof Square) {
            Square square = (Square) s;
            drawable = new SquareDrawable(square);
        }
        else if (s instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) s;
            drawable = new RectangleDrawable(rectangle);
        }
        else if (s instanceof Circle) {
            Circle circle = (Circle) s;
            drawable = new CircleDrawable(circle);
        }
        else if (s instanceof Ellipse) {
            Ellipse ellipse = (Ellipse) s;
            drawable = new EllipseDrawable(ellipse);
        }
        else if (s instanceof Triangle) {
            Triangle triangle = (Triangle) s;
            drawable = new TriangleDrawable(triangle);
        }
        else {
            throw new InvalidShapeException(
                    String.format(
                            "Invalid Shape \"%s\" given for drawable creation",
                            s.getClass().getName()
                    )
            );
        }

        if (s instanceof I3DDerived) {
            drawable.setUseTransforms(false);
        }

        return drawable;
    }

    /**
     * Creates an IDrawable for a handle.
     *
     * @param h = the model for the handle to draw.
     * @return an IDrawable for the given handle
     * @throws InvalidHandleException if the handle is not supported by the factory.
     */
    public IDrawable create(Handle h) throws InvalidHandleException {
        if (h instanceof CircleHandle) {
            CircleHandle circleHandle = (CircleHandle) h;
            return new CircleHandleDrawable(circleHandle);
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
