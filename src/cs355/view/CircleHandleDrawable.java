package cs355.view;

import cs355.model.drawing.Circle;
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
     * The handle being drawn.
     */
    private CircleHandle handle;

    /**
     * Constructor for handle drawables.
     * @param h = the handle model.
     */
    public CircleHandleDrawable(CircleHandle h) {
        this.handle = h;
    }

    @Override
    public void draw(Graphics2D g2d) {

        Circle handleCircle = (Circle) this.handle.getHandleShape();

        // Get directional parameters
        double radius = handleCircle.getRadius();

        // Calculate upper left corner of bounding rectangle
        Point2D.Double upperLeftCorner = new Point2D.Double();
        upperLeftCorner.setLocation(radius * -1, radius * -1);

        // Initialize circle geometric object
        Ellipse2D drawCircle = new Ellipse2D.Double(
                upperLeftCorner.getX(),
                upperLeftCorner.getY(),
                radius * 2,
                radius * 2
        );

        g2d.setPaint(handleCircle.getColor());
        g2d.setTransform(handleCircle.getObjToWorld());
        g2d.draw(drawCircle);
    }
}
