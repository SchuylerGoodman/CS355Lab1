package cs355.model.drawing.selectable;

import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/1/2015.
 */
public class CircleHandle extends Handle {

    private double radius;

    /**
     * Basic constructor that sets fields.
     *
     * @param shape = the shape that this handle manipulates.
     * @param center = the center point of the new shape.
     * @param radius = the radius of the handle in pixels.
     */
    public CircleHandle(Shape shape, Point2D.Double center, Color color, double radius) {
        super(shape, center, color);
        this.radius = radius;
    }

    /**
     * Getter for the radius of the handle.
     *
     * @return the radius of the handle circle in pixels as a double.
     */
    public double getRadius() {
        return this.radius;
    }

    /**
     * Setter for the radius of the handle.
     *
     * @param radius = the radius of the handle circle in pixels as a double.
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public boolean pointInShape(Point2D.Double pt, double tolerance) {

        // Get the world to object coordinates transform
        AffineTransform worldToObj = this.getShape().getWorldToObj();

        // get the point in object coordinates
        Point2D.Double ptObj = new Point2D.Double();
        worldToObj.transform(pt, ptObj);

        double radius = this.getRadius();

        // check with simple bounding box (fast)
        Point2D.Double bound = new Point2D.Double(radius, radius);
        if (Math.abs(ptObj.getX()) > bound.getX() || Math.abs(ptObj.getY()) > bound.getY()) {
            return false;
        }

        // check if the point is further from center than the radius
        double magnitude = Math.sqrt(Math.pow(ptObj.getX(), 2.0) + Math.pow(ptObj.getY(), 2.0));
        if (magnitude > radius) {
            return false;
        }

        return true;
    }
}
