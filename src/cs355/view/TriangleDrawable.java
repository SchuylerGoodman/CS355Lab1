package cs355.view;

import cs355.model.drawing.exception.InvalidShapeException;
import cs355.model.drawing.Triangle;

import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * Created by goodman on 9/9/2015.
 */
public class TriangleDrawable implements IDrawable {

    private Triangle triangle;

    public TriangleDrawable(Triangle t) throws InvalidShapeException {
        this.triangle = t;
    }

    @Override
    public void draw(Graphics2D g2d) {

        // Create triangle polygon
        GeneralPath drawTriangle = new GeneralPath();
        drawTriangle.moveTo(this.triangle.getA().getX(), this.triangle.getA().getY());
        drawTriangle.lineTo(this.triangle.getB().getX(), this.triangle.getB().getY());
        drawTriangle.lineTo(this.triangle.getC().getX(), this.triangle.getC().getY());
        drawTriangle.closePath();

        // Color and draw the triangle
        g2d.setPaint(this.triangle.getColor());
        g2d.setTransform(this.triangle.getObjToWorld());
        g2d.fill(drawTriangle);
        g2d.draw(drawTriangle);

        if (this.triangle.getSelected()) {
            g2d.setPaint(Color.WHITE);
            g2d.draw(drawTriangle);
        }
    }
}
