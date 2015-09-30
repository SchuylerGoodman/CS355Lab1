package cs355.view;

import cs355.model.drawing.*;
import cs355.model.drawing.Rectangle;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Class that controls how a rectangle is drawn.
 */
public class RectangleDrawable implements IDrawable {

    private Rectangle rectangle;

    public RectangleDrawable(Shape s) throws InvalidShapeException {
        this.setShape(s);
    }

    @Override
    public void draw(Graphics2D g2d) {
        Point2D.Double center = this.rectangle.getCenter();
        double width = this.rectangle.getWidth();
        double height = this.rectangle.getHeight();

        // Calculate upper left corner of rectangle
        Point2D.Double upperLeftCorner = new Point2D.Double();
        upperLeftCorner.setLocation((width / 2) * -1, (height / 2) * -1);

        Rectangle2D drawRectangle = new Rectangle2D.Double(
                upperLeftCorner.getX(),
                upperLeftCorner.getY(),
                width,
                height
        );

        g2d.setPaint(this.rectangle.getColor());
        g2d.setTransform(this.rectangle.getObjToWorld());
        g2d.fill(drawRectangle);
        g2d.draw(drawRectangle);
    }

    @Override
    public Shape getShape() {
        return this.rectangle;
    }

    @Override
    public void setShape(Shape s) throws InvalidShapeException {
        if (s instanceof Rectangle) {
            this.rectangle = (Rectangle) s;
        }
        else {
            this.rectangle = null;
            throw new InvalidShapeException(
                    String.format(
                            "Cannot convert input shape \"%s\" to cs355.model.drawing.Rectangle",
                            s.getClass().getName()
                    )
            );
        }
    }
}
