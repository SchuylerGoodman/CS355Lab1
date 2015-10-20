package cs355.model.drawing.selectable;

import cs355.model.drawing.Circle;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/1/2015.
 */
public abstract class CircleHandle extends Handle {

    /**
     * Basic constructor that sets fields.
     *
     * @param shape = the shape that this handle manipulates.
     * @param anchorPoint = the point (in object coordinates) on the reference shape that this handle manipulates.
     * @param center = the center point of the handle.
     * @param color = the color of the handle.
     * @param radius = the radius of the handle in pixels.
     */
    public CircleHandle(Shape shape, Point2D.Double anchorPoint, Point2D.Double center, Color color, double radius) {
        super(shape, anchorPoint);

        Circle handleShape = new Circle(color, center, radius);
        this.setHandleShape(handleShape);
    }
}
