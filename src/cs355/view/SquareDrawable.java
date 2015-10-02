package cs355.view;

import cs355.model.drawing.*;
import cs355.model.drawing.exception.InvalidShapeException;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Class that controls how a square is drawn.
 */
public class SquareDrawable extends ShapeDrawable {

    public SquareDrawable(Square s) throws InvalidShapeException {
        super(s);
    }

    @Override
    public void draw(Graphics2D g2d) {

        Square square = (Square) this.shape;
        double size = square.getSize();

        // Calculate upper left corner of bounding rectangle
        Point2D.Double upperLeftCorner = new Point2D.Double();
        upperLeftCorner.setLocation(size / 2 * -1, size / 2 * -1);

        Rectangle2D drawSquare = new Rectangle2D.Double(
                upperLeftCorner.getX(),
                upperLeftCorner.getY(),
                size,
                size
        );

        g2d.setPaint(square.getColor());
        g2d.setTransform(square.getObjToWorld());
        g2d.fill(drawSquare);
        g2d.draw(drawSquare);

        if (square.getSelected()) {
            g2d.setPaint(Color.WHITE);
            g2d.draw(drawSquare);
        }

        super.draw(g2d);
    }
}

