package cs355.view;

import cs355.model.drawing.*;

/**
 * Created by goodman on 9/9/2015.
 */
public class DrawableFactory {
    public IDrawable create(Shape s) throws InvalidShapeException {
        if (s instanceof Line) {
            return new LineDrawable(s);
        }
        else if (s instanceof Square) {
            return new SquareDrawable(s);
        }
        else if (s instanceof Rectangle) {
            return new RectangleDrawable(s);
        }
        else if (s instanceof Circle) {
            return new CircleDrawable(s);
        }
        else if (s instanceof Ellipse) {
            return new EllipseDrawable(s);
        }
        else if (s instanceof Triangle) {
            return new TriangleDrawable(s);
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
}
