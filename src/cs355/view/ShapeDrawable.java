package cs355.view;

import cs355.GUIFunctions;
import cs355.model.drawing.selectable.Handle;
import cs355.model.exception.InvalidHandleException;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by BaronVonBaerenstein on 10/1/2015.
 */
public abstract class ShapeDrawable implements IDrawable {

    protected Shape shape;

    public ShapeDrawable(Shape s) {
        this.shape = s;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (this.shape.getSelected()) {
            this.drawHandles(g2d);
        }
    }

    /**
     * Method for drawing the handles for a shape.
     * @param g2d = the graphics object on which to draw the handles.
     */
    protected void drawHandles(Graphics2D g2d) {

        DrawableFactory factory = new DrawableFactory();

        ArrayList<Handle> handles = this.shape.getHandles();
        for (Handle handle : handles) {
            try {
                IDrawable drawHandle = factory.create(handle, this.shape);
                drawHandle.draw(g2d);
            }
            catch (InvalidHandleException e) {
                GUIFunctions.printf(
                        String.format(
                                "Invalid handle encountered:\n %s",
                                e.getMessage()
                        )
                );
            }
        }
    }
}
