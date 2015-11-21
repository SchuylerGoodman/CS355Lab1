package cs355.view;

import cs355.GUIFunctions;
import cs355.model.drawing.selectable.Handle;
import cs355.model.exception.InvalidHandleException;
import cs355.model.drawing.Shape;
import cs355.model.view.IViewModel;

import java.awt.*;
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
    public void draw(Graphics2D g2d, IViewModel viewModel) {

        // Regulate the stroke, so the width of the border doesn't change with the zoom level
        float strokeWidth = 2 / (float) viewModel.getZoomFactor();
        g2d.setStroke(new BasicStroke(strokeWidth));

        if (this.shape.getSelected()) {
            this.drawHandles(g2d, viewModel);
        }
    }

    /**
     * Method for drawing the handles for a shape.
     * @param g2d = the graphics object on which to draw the handles.
     * @param viewModel = transformation from world space to view space.
     */
    protected void drawHandles(Graphics2D g2d, IViewModel viewModel) {

        DrawableFactory factory = new DrawableFactory();

        ArrayList<Handle> handles = this.shape.getHandles();
        for (Handle handle : handles) {
            try {
                IDrawable drawHandle = factory.create(handle);
                drawHandle.draw(g2d, viewModel);
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
