package cs355.view;

import cs355.model.drawing.*;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Class that controls how a square is drawn.
 */
public class SquareDrawable implements IDrawable {

    private Square square;

    public SquareDrawable(Shape s) throws InvalidShapeException {
        this.setShape(s);
    }

    @Override
    public void draw(Graphics2D g2d) {
        Point2D.Double center = this.square.getCenter();
        double size = this.square.getSize();

        // Calculate upper left corner of bounding rectangle
        Point2D.Double upperLeftCorner = new Point2D.Double();
        upperLeftCorner.setLocation(size / 2 * -1, size / 2 * -1);

        Rectangle2D drawSquare = new Rectangle2D.Double(
                upperLeftCorner.getX(),
                upperLeftCorner.getY(),
                size,
                size
        );

        g2d.setPaint(this.square.getColor());
        g2d.setTransform(this.square.getObjToWorld());
        g2d.fill(drawSquare);
        g2d.draw(drawSquare);

        if (this.square.getSelected()) {
            g2d.setPaint(Color.WHITE);
            g2d.draw(drawSquare);
        }
    }

    @Override
    public Shape getShape() {
        return this.square;
    }

    @Override
    public void setShape(Shape s) throws InvalidShapeException {
        if (s instanceof Square) {
            this.square = (Square) s;
        }
        else {
            this.square = null;
            throw new InvalidShapeException(
                    String.format(
                            "Cannot convert input shape \"%s\" to cs355.model.drawing.Square",
                            s.getClass().getName()
                    )
            );
        }
    }
}

