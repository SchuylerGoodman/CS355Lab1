package cs355.view;

import cs355.GUIFunctions;
import cs355.model.drawing.*;
import cs355.model.exception.InvalidModelException;

import java.awt.Graphics2D;
import java.util.*;

/**
 * Created by goodman on 9/8/2015.
 */
public class ViewRefresherImpl implements ViewRefresher {

    private CS355Drawing model;

    public ViewRefresherImpl(CS355Drawing model) {
        this.model = model;
    }

    @Override
    public void refreshView(Graphics2D g2d) {

        // Query the shapes from the model
        List<Shape> shapes = this.model.getShapes();

        // Initialize the factory for the drawables
        DrawableFactory drawableFactory = new DrawableFactory();

        // Get an iterator for the shapes in the model
        ListIterator<Shape> iterator = shapes.listIterator();

        while (iterator.hasNext()) {

            // Get the next shape in the model
            Shape s = iterator.next();

            // Try to create the drawable for this shape
            try {
                IDrawable drawable = drawableFactory.create(s);
                drawable.draw(g2d);
            }
            catch (InvalidModelException e) {
                GUIFunctions.printf(
                        "Shape in model could not be found, or an error was encountered while creating the drawable: \"%s\"",
                        e.getMessage()
                );
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        // Force the view refresh
        GUIFunctions.refresh();
    }
}
