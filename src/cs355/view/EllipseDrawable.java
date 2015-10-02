package cs355.view;

import cs355.model.drawing.Ellipse;
import cs355.model.exception.InvalidShapeException;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by goodman on 9/9/2015.
 */
public class EllipseDrawable extends ShapeDrawable {

    public EllipseDrawable(Ellipse e) throws InvalidShapeException {
        super(e);
    }

    @Override
    public void draw(Graphics2D g2d) {

        Ellipse ellipse = (Ellipse) this.shape;
        
        // Get directional parameters
        double width = ellipse.getWidth();
        double height = ellipse.getHeight();

        // Calculate upper left corner of bounding rectangle
        Point2D.Double upperLeftCorner = new Point2D.Double();
        upperLeftCorner.setLocation((width / 2) * -1, (height / 2) * -1);

        // Initialize ellipse geometric object
        Ellipse2D drawEllipse = new Ellipse2D.Double(
                upperLeftCorner.getX(),
                upperLeftCorner.getY(),
                width,
                height
        );

        g2d.setPaint(ellipse.getColor());
        g2d.setTransform(ellipse.getObjToWorld());
        g2d.fill(drawEllipse);
        g2d.draw(drawEllipse);

        if (ellipse.getSelected()) {
            g2d.setPaint(Color.WHITE);

            // Initialize rectangle geometric object
            Rectangle2D drawRectangle = new Rectangle2D.Double(
                    upperLeftCorner.getX(),
                    upperLeftCorner.getY(),
                    width,
                    height
            );
            g2d.draw(drawRectangle);
        }

        super.draw(g2d);
    }
}

