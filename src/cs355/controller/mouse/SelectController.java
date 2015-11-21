package cs355.controller.mouse;

import cs355.controller.CS355Controller;
import cs355.model.drawing.*;
import cs355.model.drawing.Shape;
import cs355.model.drawing.selectable.Handle;
import cs355.model.view.IViewModel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

/**
 * Class that handles input for selecting and manipulating objects.
 */
public class SelectController implements IMouseEventController, Observer {

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
     * Model that controls the size and orientation of the view.
     */
    private IViewModel viewModel;

    /**
     * The shape which has been selected.
     */
    private Shape selectedShape;

    /**
     * Factory for creating handle controllers.
     */
    private HandleControllerFactory handleControllerFactory;

    /**
     * Controller for handling different actions that can occur when an object's handle is manipulated.
     */
    private HandleController handleController;

    /**
     * Constructor for the SelectController class.
     *
     * @param controller = the main controller for the drawing.
     * @param colorChangeNotifier = an observable that notifies this controller if the color has changed.
     * @param viewModel = a model for the view, which stores information about the orientation.
     */
    public SelectController(CS355Controller controller, Observable colorChangeNotifier, IViewModel viewModel) {
        initialCoordinates = new Point2D.Double();
        this.controller = controller;
        this.viewModel = viewModel;
        this.selectedShape = null;

        this.handleControllerFactory = new HandleControllerFactory();
        this.handleController = null;

        colorChangeNotifier.addObserver(this);
    }

    @Override
    public void mouseClicked(MouseEvent e, CS355Drawing model, Color c) {

        // If a handle has been selected, default to that behavior instead.
        if (!this.handleSelected()) {
        }
        else {
            this.handleController.mouseClicked(e, model, c);
        }

        this.updateSelectedShapeHandles();
    }

    @Override
    public void mousePressed(MouseEvent e, CS355Drawing model, Color c) {

        // Set initial coordinates
        initialCoordinates.setLocation(e.getPoint());
        this.deselectHandle();

        // If another shape is currently selected, see if we have selected a handle.
        // If not, deselect the shape and find the newly selected shape.
        if (this.shapeSelected()) {
            ListIterator<Handle> handleIterator = this.selectedShape.getHandles().listIterator();
            while (!this.handleSelected() && handleIterator.hasNext()) {
                Handle handle = handleIterator.next();
                if (handle.pointInside(initialCoordinates, 0.0)) {
                    this.handleController = this.handleControllerFactory.create(handle);
                }
            }
        }

        // If a handle has been selected, default to that behavior instead.
        if (!this.handleSelected()) {

            this.deselectShape();

            // Get the shapes from the model from top to bottom
            List<Shape> shapes = model.getShapesReversed();

            // Loop through the shapes to find the top one that intersects with the point
            ListIterator<Shape> shapeIterator = shapes.listIterator();
            while (shapeIterator.hasNext()) {
                Shape shape = shapeIterator.next();

                // Normalize selection tolerance by zoom factor.
                double tolerance = SelectController.SELECTION_TOLERANCE / this.viewModel.getZoomFactor();
                if (shape.pointInShape(this.initialCoordinates, tolerance)) {

                    // Select the new shape
                    this.selectShape(shape);
                    this.controller.colorButtonHit(this.selectedShape.getColor());
                    break;
                }
            }
        }
        else {
            this.handleController.mousePressed(e, model, c);
        }

        this.updateSelectedShapeHandles();
    }

    @Override
    public void mouseReleased(MouseEvent e, CS355Drawing model, Color c) {

        // If a handle has been selected, default to that behavior instead.
        if (!this.handleSelected()) {
        }
        else {
            this.handleController.mouseReleased(e, model, c);
        }

        this.updateSelectedShapeHandles();
    }

    @Override
    public void mouseEntered(MouseEvent e, CS355Drawing model, Color c) {

        // If a handle has been selected, default to that behavior instead.
        if (!this.handleSelected()) {
        }
        else {
            this.handleController.mouseEntered(e, model, c);
        }

        this.updateSelectedShapeHandles();
    }

    @Override
    public void mouseExited(MouseEvent e, CS355Drawing model, Color c) {

        // If a handle has been selected, default to that behavior instead.
        if (!this.handleSelected()) {
        }
        else {
            this.handleController.mouseExited(e, model, c);
        }

        this.updateSelectedShapeHandles();
    }

    @Override
    public void mouseDragged(MouseEvent e, CS355Drawing model, Color c) {

        // If nothing selected, dragging does nothing.
        if (!this.shapeSelected()) {
            return;
        }

        // If a handle has been selected, default to that behavior instead.
        if (!this.handleSelected()) {
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
        else {
            this.handleController.mouseDragged(e, model, c);
        }

        this.updateSelectedShapeHandles();
    }

    @Override
    public void mouseMoved(MouseEvent e, CS355Drawing model, Color c) {

        // If a handle has been selected, default to that behavior instead.
        if (!this.handleSelected()) {
        }
        else {
            this.handleController.mouseMoved(e, model, c);
        }

        this.updateSelectedShapeHandles();
    }

    @Override
    public void close() {
        this.deselectShape();
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

    private void selectShape(Shape shape) {
        this.deselectShape();
        this.selectedShape = shape;
        this.selectedShape.setSelected(true);
    }

    private void deselectShape() {
        if (this.shapeSelected()) {
            this.selectedShape.setSelected(false);
            this.selectedShape = null;
        }
        this.deselectHandle();
    }

    private boolean handleSelected() {
        return this.handleController != null;
    }

    private void deselectHandle() {
        if (this.handleSelected()) {
            this.handleController = null;
        }
    }

    private void updateSelectedShapeHandles() {
        if (this.shapeSelected()) {
            this.selectedShape.updateHandles(this.viewModel.getZoomFactor());
        }
    }
}
