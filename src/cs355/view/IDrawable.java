package cs355.view;

import cs355.model.drawing.InvalidShapeException;
import cs355.model.drawing.Shape;

import java.awt.*;

/**
 * Created by goodman on 9/9/2015.
 */
public interface IDrawable {
    void draw(Graphics2D g2d);

    /**
     * Gets the shape assigned to the drawable.
     *
     * @return the shape assigned to the drawable
     */
    Shape getShape();

    /**
     * Attempts to set the shape for the drawable.
     *
     * @param s = the shape that the drawable will draw.
     * @return true if the shape is compatible with the drawable, false otherwise.
     */
    void setShape(Shape s) throws InvalidShapeException;
}
