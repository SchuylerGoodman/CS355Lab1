package cs355.model.drawing.selectable;

import cs355.model.drawing.Line;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/19/2015.
 */
public class HandleFactory {

    protected static Color HANDLE_COLOR = Color.WHITE;
    protected static double HANDLE_RADIUS = 5.0;

    public Handle create(Shape shape, Point2D.Double anchorPoint, Point2D.Double center){
        if (shape instanceof Line) {
            return new DragHandle(
                    shape,
                    anchorPoint,
                    center,
                    HANDLE_COLOR,
                    HANDLE_RADIUS
            );
        }
        else {
            return new RotationHandle(
                    shape,
                    anchorPoint,
                    center,
                    HANDLE_COLOR,
                    HANDLE_RADIUS
            );
        }
    }
}
