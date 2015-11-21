package cs355.controller.mouse;

import cs355.GUIFunctions;
import cs355.model.drawing.*;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Class that handles input for drawing squares.
 */
public class SquareController implements IMouseEventController {

    /**
     * The initial coordinates of the mouse press.
     */
    private Point2D.Double initialCoordinates;

    /**
     * The index of the shape in the drawing model.
     */
    private int index;

    public SquareController() {
        initialCoordinates = new Point2D.Double();
        this.index = -1;
    }

    @Override
    public void mouseClicked(MouseEvent e, CS355Drawing model, Color c) {
    }

    @Override
    public void mousePressed(MouseEvent e, CS355Drawing model, Color c) {

        // Set initial coordinates
        initialCoordinates.setLocation(e.getPoint());

        // Create new line
        Square square = new Square(c, initialCoordinates, 0.0);

        // Add line to model and save index
        this.index = model.addShape(square);
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

        // Get shape at saved index from model and verify it is a square
        Shape shape = model.getShape(this.index);
        if (!(shape instanceof cs355.model.drawing.Square)) {
            GUIFunctions.printf("Invalid shape - expected cs355.model.drawing.Square at index %d", this.index);
            return;
        }

        // Cast the shape to a square and save the new coordinates
        Square square = (Square) shape;

        // Initialize new top left corner coordinates to initial coordinates
        Point2D.Double upperLeftCorner = new Point2D.Double();
        upperLeftCorner.setLocation(this.initialCoordinates);

        // Get current pointer coordinates
        Point2D.Double currentCoordinates = new Point2D.Double();
        currentCoordinates.setLocation(e.getPoint());

        // Find difference between pointer and initial coordinates to get correct orientation
        double xDifference = currentCoordinates.getX() - initialCoordinates.getX();
        double yDifference = currentCoordinates.getY() - initialCoordinates.getY();

        // Get side size
        double size = Math.min(
                Math.abs(xDifference),
                Math.abs(yDifference)
        );

        // Calculate position of top-left corner
        if (xDifference < 0) {
            upperLeftCorner.x = this.initialCoordinates.getX() - size;
        }
        if (yDifference < 0) {
            upperLeftCorner.y = this.initialCoordinates.getY() - size;
        }

        // Set the new parameters
        Point2D.Double center = new Point2D.Double(
                upperLeftCorner.getX() + size / 2,
                upperLeftCorner.getY() + size / 2
        );
        square.setCenter(center);
        square.setSize(size);
    }

    @Override
    public void mouseMoved(MouseEvent e, CS355Drawing model, Color c) {
    }

    @Override
    public void close() {

    }
}

