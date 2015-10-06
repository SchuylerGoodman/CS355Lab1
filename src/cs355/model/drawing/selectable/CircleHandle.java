package cs355.model.drawing.selectable;

import cs355.model.drawing.Circle;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/1/2015.
 */
public class CircleHandle extends Handle {

    /**
     * Basic constructor that sets fields.
     *
     * @param shape = the shape that this handle manipulates.
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

        return this.getHandleShape().pointInShape(pt, tolerance);
        /*
        // Get the world to object coordinates transform
        AffineTransform worldToObj = this.getReferenceShape().getWorldToObj();

        // Get the point in object coordinates
        Shape handleShape = this.getHandleShape();
        Point2D.Double ptObj = new Point2D.Double();
        worldToObj.transform(pt, ptObj);
        ptObj.setLocation(
                ptObj.getX() - handleShape.getCenter().getX(),
                ptObj.getY() - handleShape.getCenter().getY()
        );

        Circle handleCircle = (Circle) handleShape;
        double radius = handleCircle.getRadius();

        // Check with simple bounding box (fast)
        Point2D.Double bound = new Point2D.Double(radius, radius);
        if (Math.abs(ptObj.getX()) > bound.getX() || Math.abs(ptObj.getY()) > bound.getY()) {
            return false;
        }

        // check if the point is further from center than the radius
        double magnitude = Math.sqrt(Math.pow(ptObj.getX(), 2.0) + Math.pow(ptObj.getY(), 2.0));
        if (magnitude > radius) {
            return false;
        }

        return true;*/
    }
}
