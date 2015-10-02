package cs355.view;

import cs355.model.drawing.Ellipse;
import cs355.model.drawing.exception.InvalidShapeException;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Created by goodman on 9/9/2015.
 */
public class EllipseDrawable implements IDrawable {

    private Ellipse ellipse;

    public EllipseDrawable(Ellipse e) throws InvalidShapeException {
        this.ellipse = e;
    }

    @Override
    public void draw(Graphics2D g2d) {

        // Get directional parameters
        double width = this.ellipse.getWidth();
        double height = this.ellipse.getHeight();

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

        g2d.setPaint(this.ellipse.getColor());
        g2d.setTransform(this.ellipse.getObjToWorld());
        g2d.fill(drawEllipse);
        g2d.draw(drawEllipse);

        if (this.ellipse.getSelected()) {
            g2d.setPaint(Color.WHITE);
            g2d.draw(drawEllipse);
        }
    }
}

