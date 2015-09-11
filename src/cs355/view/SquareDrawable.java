package cs355.view;

import cs355.model.drawing.*;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by goodman on 9/10/2015.
 */
public class SquareDrawable implements IDrawable {

    private Square square;

    public SquareDrawable(Shape s) throws InvalidShapeException {
        this.setShape(s);
    }

    @Override
    public void draw(Graphics2D g2d) {
        Point2D.Double upperLeftCorner = this.square.getUpperLeft();
        Rectangle2D drawSquare = new Rectangle2D.Double(
                upperLeftCorner.getX(),
                upperLeftCorner.getY(),
                this.square.getSize(),
                this.square.getSize()
        );

        g2d.setPaint(this.square.getColor());
        g2d.fill(drawSquare);
        g2d.draw(drawSquare);
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

