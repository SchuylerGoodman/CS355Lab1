package cs355.view;

import cs355.model.drawing.Shape;
import cs355.model.drawing.selectable.CircleHandle;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/1/2015.
 */
public class CircleHandleDrawable implements IDrawable {

    /**
     * The shape the the handle is used to manipulate.
     */
    private Shape handledShape;

    /**
     * The handle being drawn.
     */
    private CircleHandle handle;

    /**
     * Constructor for handle drawables.
     * @param handledShape = the model for the shape that the handle is used to manipulate.
     * @param h = the handle model.
     */
    public CircleHandleDrawable(Shape handledShape, CircleHandle h) {
        this.handledShape = handledShape;
        this.handle = h;
    }

    @Override
    public void draw(Graphics2D g2d) {

        // Get directional parameters
        double radius = this.handle.getRadius();

        // Calculate upper left corner of bounding rectangle
        Point2D.Double upperLeftCorner = new Point2D.Double();
        upperLeftCorner.setLocation(
                this.handle.getCenter().getX() - radius,
                this.handle.getCenter().getY() - radius
        );

        // Initialize circle geometric object
        Ellipse2D drawCircle = new Ellipse2D.Double(
                upperLeftCorner.getX(),
                upperLeftCorner.getY(),
                radius * 2,
                radius * 2
        );

        g2d.setPaint(this.handle.getColor());
        g2d.setTransform(this.handledShape.getObjToWorld());
        g2d.draw(drawCircle);
    }
}
