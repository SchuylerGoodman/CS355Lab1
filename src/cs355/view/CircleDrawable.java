package cs355.view;

import cs355.model.drawing.*;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Created by goodman on 9/10/2015.
 */
public class CircleDrawable implements IDrawable {

    private Circle circle;

    public CircleDrawable(Shape s) throws InvalidShapeException {
        this.setShape(s);
    }

    @Override
    public void draw(Graphics2D g2d) {

        // Get directional parameters
        Point2D.Double center = this.circle.getCenter();
        double radius = this.circle.getRadius();

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

        g2d.setPaint(this.circle.getColor());
        g2d.setTransform(this.circle.getObjToWorld());
        g2d.fill(drawCircle);
        g2d.draw(drawCircle);

        if (this.circle.getSelected()) {
            g2d.setPaint(Color.WHITE);
            g2d.draw(drawCircle);
        }
    }

    @Override
    public Shape getShape() {
        return this.circle;
    }

    @Override
    public void setShape(Shape s) throws InvalidShapeException {
        if (s instanceof Circle) {
            this.circle = (Circle) s;
        }
        else {
            this.circle = null;
            throw new InvalidShapeException(
                    String.format(
                            "Cannot convert input shape \"%s\" to cs355.model.drawing.Circle",
                            s.getClass().getName()
                    )
            );
        }
    }
}

