package cs355.view;

import cs355.model.drawing.*;
import cs355.model.exception.InvalidShapeException;
import cs355.model.view.IViewModel;

import java.awt.*;
import java.awt.geom.AffineTransform;
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
    public void draw(Graphics2D g2d, IViewModel viewModel) {

        super.draw(g2d, viewModel);

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

        // Concatenate objToWorld and worldToView transforms to get objToView
        AffineTransform objToView = new AffineTransform(viewModel.getWorldToView());
        objToView.concatenate(square.getObjToWorld());

        g2d.setPaint(square.getColor());
        g2d.setTransform(objToView);
        g2d.fill(drawSquare);
        g2d.draw(drawSquare);

        if (square.getSelected()) {
            g2d.setPaint(Color.WHITE);
            g2d.draw(drawSquare);
        }
    }
}

