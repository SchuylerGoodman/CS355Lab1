package cs355.view;

import cs355.model.exception.InvalidShapeException;
import cs355.model.drawing.Rectangle;
import cs355.model.view.IViewModel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Class that controls how a rectangle is drawn.
 */
public class RectangleDrawable extends ShapeDrawable {

    public RectangleDrawable(Rectangle r) throws InvalidShapeException {
        super(r);
    }

    @Override
    public void draw(Graphics2D g2d, IViewModel viewModel) {

        super.draw(g2d, viewModel);

        Rectangle rectangle = (Rectangle) this.shape;
        
        double width = rectangle.getWidth();
        double height = rectangle.getHeight();

        // Calculate upper left corner of rectangle
        Point2D.Double upperLeftCorner = new Point2D.Double();
        upperLeftCorner.setLocation((width / 2) * -1, (height / 2) * -1);

        Rectangle2D drawRectangle = new Rectangle2D.Double(
                upperLeftCorner.getX(),
                upperLeftCorner.getY(),
                width,
                height
        );

        // Concatenate objToWorld and worldToView transforms to get objToView
        AffineTransform objToView = new AffineTransform(viewModel.getWorldToView());
        objToView.concatenate(rectangle.getObjToWorld());

        g2d.setPaint(rectangle.getColor());
        g2d.setTransform(objToView);
        g2d.fill(drawRectangle);
        g2d.draw(drawRectangle);

        if (rectangle.getSelected()) {
            g2d.setPaint(Color.WHITE);
            g2d.draw(drawRectangle);
        }
    }
}
