package cs355.controller;

import cs355.GUIFunctions;
import cs355.model.drawing.*;
import cs355.model.drawing.Shape;
import cs355.model.drawing.selectable.Handle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
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
     * The initial coordinates of the mouse press.
     */
    private Point2D.Double initialCoordinates;

    /**
     * The controller instance that is managing events.
     */
    private CS355Controller controller;

    /**
     * The shape which has been selected.
     */
    private Shape selectedShape;

    /**
     * Controller for handling different actions that can occur while an object is selected.
     */
    private IController selectActionController;

    public SelectController(CS355Controller controller, Observable colorChangeNotifier) {
        initialCoordinates = new Point2D.Double();
        this.controller = controller;
        this.selectedShape = null;

        colorChangeNotifier.addObserver(this);
    }

    @Override
    public void mouseClicked(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void mousePressed(MouseEvent e, CS355Drawing model, Color c) {

        // Set initial coordinates
        initialCoordinates.setLocation(e.getPoint());

        // If another shape is currently selected, see if we have selected a handle.
        // If not, deselect the shape and find the newly selected shape.
        if (this.shapeSelected()) {
            for (Handle handle : this.selectedShape.getHandles()) {
                if (handle.pointInShape(initialCoordinates, 0.0)) {
                    // TODO create handle for rotation
                    // TODO create handle for moving line endpoints
                }
            }
            this.selectedShape.setSelected(false);
            this.selectedShape = null;
        }

        // Get the shapes from the model from top to bottom
        List<Shape> shapes = model.getShapesReversed();

        // Loop through the shapes to find the top one that intersects with the point
        ListIterator<Shape> shapeIterator = shapes.listIterator();
        while (shapeIterator.hasNext()) {
            Shape shape = shapeIterator.next();
            if (shape.pointInShape(this.initialCoordinates, SelectController.SELECTION_TOLERANCE)) {

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

        // If nothing selected, dragging does nothing.
        if (!this.shapeSelected()) {
            return;
        }

        // Get the current pointer location.
        Point2D.Double current = new Point2D.Double();
        current.setLocation(e.getPoint());

        // Find the distance moved since the initial location.
        double xDiff = current.getX() - this.initialCoordinates.getX();
        double yDiff = current.getY() - this.initialCoordinates.getY();

        // Get the center of the selected shape.
        Point2D.Double center = this.selectedShape.getCenter();

        // Get the destination for the center of the shape.
        Point2D.Double destination = new Point2D.Double(
                center.getX() + xDiff,
                center.getY() + yDiff
        );

        // Set the initial location to the current point, for the next drag.
        this.initialCoordinates.setLocation(current);

        // Update the center of the shape.
        this.selectedShape.setCenter(destination);
    }

    @Override
    public void mouseMoved(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void close() {
        if (this.shapeSelected()) {
            this.selectedShape.setSelected(false);
            this.selectedShape = null;
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        // If observable has updated color, change the selected object's color.
        if (arg instanceof Color && this.shapeSelected()) {
            Color color = (Color) arg;
            this.selectedShape.setColor(color);
        }
    }

    private boolean shapeSelected() {
        return this.selectedShape != null;
    }
}
