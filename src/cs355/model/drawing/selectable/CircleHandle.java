package cs355.model.drawing.selectable;

import cs355.model.drawing.Circle;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/1/2015.
 */
public abstract class CircleHandle extends Handle {

    protected static double HANDLE_RADIUS = 5.0;

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

    @Override
    public boolean pointInside(Point2D.Double pt, double tolerance) {
        Point2D.Double objPoint = new Point2D.Double();
        AffineTransform worldToObj = this.getReferenceShape().getWorldToObj();
        worldToObj.transform(pt, objPoint);

        // Have to normalize the radius with the zoom when we are doing bounds checking,
        // because handles stay the same visual size at different zoom levels.
        double radius = this.getHandleCircle().getRadius();
        this.getHandleCircle().setRadius(radius / this.zoomFactor);
        boolean pointInShape = this.getHandleShape().pointInShape(objPoint, tolerance);
        this.getHandleCircle().setRadius(radius);

        return pointInShape;
    }


    /**
     * Gets the handle shape, but as a circle.
     *
     * @return the circle that represents the "location" of this handle in the drawing.
     */
    public Circle getHandleCircle() {
        return (Circle) this.getHandleShape();
    }
}
