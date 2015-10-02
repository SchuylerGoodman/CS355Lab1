package cs355.view;

import cs355.model.exception.InvalidShapeException;
import cs355.model.drawing.Triangle;

import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * Created by goodman on 9/9/2015.
 */
public class TriangleDrawable extends ShapeDrawable {

    public TriangleDrawable(Triangle t) throws InvalidShapeException {
        super(t);
    }

    @Override
    public void draw(Graphics2D g2d) {

        Triangle triangle = (Triangle) this.shape;
        
        // Create triangle polygon
        GeneralPath drawTriangle = new GeneralPath();
        drawTriangle.moveTo(triangle.getA().getX(), triangle.getA().getY());
        drawTriangle.lineTo(triangle.getB().getX(), triangle.getB().getY());
        drawTriangle.lineTo(triangle.getC().getX(), triangle.getC().getY());
        drawTriangle.closePath();

        // Color and draw the triangle
        g2d.setPaint(triangle.getColor());
        g2d.setTransform(triangle.getObjToWorld());
        g2d.fill(drawTriangle);
        g2d.draw(drawTriangle);

        if (triangle.getSelected()) {
            g2d.setPaint(Color.WHITE);
            g2d.draw(drawTriangle);
        }

        super.draw(g2d);
    }
}
