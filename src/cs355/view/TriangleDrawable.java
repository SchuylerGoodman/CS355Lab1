package cs355.view;

import cs355.model.exception.InvalidShapeException;
import cs355.model.drawing.Triangle;
import cs355.model.view.AbstractViewModel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

/**
 * Created by goodman on 9/9/2015.
 */
public class TriangleDrawable extends ShapeDrawable {

    public TriangleDrawable(Triangle t) throws InvalidShapeException {
        super(t);
    }

    @Override
    public void draw(Graphics2D g2d, AbstractViewModel viewModel) {

        super.draw(g2d, viewModel);

        Triangle triangle = (Triangle) this.shape;
        
        // Create triangle polygon
        GeneralPath drawTriangle = new GeneralPath();
        drawTriangle.moveTo(triangle.getA().getX(), triangle.getA().getY());
        drawTriangle.lineTo(triangle.getB().getX(), triangle.getB().getY());
        drawTriangle.lineTo(triangle.getC().getX(), triangle.getC().getY());
        drawTriangle.closePath();

        // Concatenate objToWorld and worldToView transforms to get objToView
        AffineTransform objToView = new AffineTransform(viewModel.getWorldToView());
        objToView.concatenate(triangle.getObjToWorld());

        // Color and draw the triangle
        g2d.setPaint(triangle.getColor());
        g2d.setTransform(objToView);
        g2d.fill(drawTriangle);
        g2d.draw(drawTriangle);

        if (triangle.getSelected()) {
            g2d.setPaint(Color.WHITE);
            g2d.draw(drawTriangle);
        }
    }
}
