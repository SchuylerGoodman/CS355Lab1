package cs355.view;

import cs355.model.drawing.InvalidShapeException;
import cs355.model.drawing.Shape;
import cs355.model.drawing.Triangle;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

/**
 * Created by goodman on 9/9/2015.
 */
public class TriangleDrawable implements IDrawable {

    private Triangle triangle;

    public TriangleDrawable(Shape s) throws InvalidShapeException {
        this.setShape(s);
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
        g2d.fill(drawTriangle);
        g2d.draw(drawTriangle);
    }

    @Override
    public Shape getShape() {
        return this.triangle;
    }

    @Override
    public void setShape(Shape s) throws InvalidShapeException {
        if (s instanceof Triangle) {
            this.triangle = (Triangle) s;
        }
        else {
            this.triangle = null;
            throw new InvalidShapeException(
                    String.format(
                            "Cannot convert input shape \"%s\" to cs355.model.drawing.Triangle",
                            s.getClass().getName()
                    )
            );
        }
    }
}
