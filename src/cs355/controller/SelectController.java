package cs355.controller;

import cs355.GUIFunctions;
import cs355.model.drawing.*;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * Class that handles input for selecting and manipulating objects.
 */
public class SelectController implements IController, Observer {

    /**
     * The number of pixels of tolerance from a line that will still result in a selection.
     */
    private static double SELECTION_TOLERANCE = 4.0;

    /**
     * The controller instance that is managing events.
     */
    private CS355Controller controller;

    /**
     * The shape which has been selected.
     */
    private Shape selectedShape;

    public SelectController(CS355Controller controller, Observable colorChangeNotifier) {
        this.controller = controller;
        this.selectedShape = null;

        colorChangeNotifier.addObserver(this);
    }

    @Override
    public void mouseClicked(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void mousePressed(MouseEvent e, CS355Drawing model, Color c) {

        Point2D.Double location = new Point2D.Double();
        location.setLocation(e.getPoint());

        // Get the shapes from the model from top to bottom
        List<Shape> shapes = model.getShapesReversed();

        // If another shape is currently selected, deselect it
        if (this.shapeSelected()) {
            this.selectedShape.setSelected(false);
            this.selectedShape = null;
        }

        // Loop through the shapes to find the top one that intersects with the point
        ListIterator<Shape> shapeIterator = shapes.listIterator();
        while (shapeIterator.hasNext()) {
            Shape shape = shapeIterator.next();
            if (shape.pointInShape(location, SelectController.SELECTION_TOLERANCE)) {

                // Select the new shape
                this.selectedShape = shape;
                this.selectedShape.setSelected(true);
                this.controller.colorButtonHit(this.selectedShape.getColor());
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void mouseEntered(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void mouseExited(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void mouseDragged(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void mouseMoved(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void close() {
        this.selectedShape.setSelected(false);
        this.selectedShape = null;
    }

    private boolean shapeSelected() {
        return this.selectedShape != null;
    }

    @Override
    public void update(Observable o, Object arg) {

        // If observable has updated color, change the selected object's color.
        if (arg instanceof Color && this.shapeSelected()) {
            Color color = (Color) arg;
            this.selectedShape.setColor(color);
        }
    }
}
