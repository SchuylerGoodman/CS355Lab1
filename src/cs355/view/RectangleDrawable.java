package cs355.view;

import cs355.model.drawing.exception.InvalidShapeException;
import cs355.model.drawing.Rectangle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Class that controls how a rectangle is drawn.
 */
public class RectangleDrawable implements IDrawable {

    private Rectangle rectangle;

    public RectangleDrawable(Rectangle r) throws InvalidShapeException {
        this.rectangle = r;
    }

    @Override
    public void draw(Graphics2D g2d) {
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

        if (this.rectangle.getSelected()) {
            g2d.setPaint(Color.WHITE);
            g2d.draw(drawRectangle);
        }
    }
}
