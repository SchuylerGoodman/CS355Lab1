package cs355.view;

import cs355.model.drawing.*;
import cs355.model.exception.InvalidShapeException;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by goodman on 9/10/2015.
 */
public class CircleDrawable extends ShapeDrawable {

    public CircleDrawable(Circle c) throws InvalidShapeException {
        super(c);
    }

    @Override
    public void draw(Graphics2D g2d) {

        Circle circle = (Circle) this.shape;

        // Get directional parameters
        double radius = circle.getRadius();

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

        g2d.setPaint(circle.getColor());
        g2d.setTransform(circle.getObjToWorld());
        g2d.fill(drawCircle);
        g2d.draw(drawCircle);

        if (circle.getSelected()) {
            g2d.setPaint(Color.WHITE);

            // Initialize rectangle geometric object
            Rectangle2D drawSquare = new Rectangle2D.Double(
                    upperLeftCorner.getX(),
                    upperLeftCorner.getY(),
                    radius * 2,
                    radius * 2
            );
            g2d.draw(drawSquare);
        }

        super.draw(g2d);
    }
}

