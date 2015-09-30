package cs355.controller;

import cs355.GUIFunctions;
import cs355.model.drawing.*;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Created by goodman on 9/10/2015.
 */
public class CircleController implements IController {

    /**
     * The initial coordinates of the mouse press.
     */
    private Point2D.Double initialCoordinates;

    /**
     * The index of the shape in the drawing model.
     */
    private int index;

    public CircleController() {
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
        Circle circle = new Circle(c, initialCoordinates, 0.0);

        // Add line to model and save index
        this.index = model.addShape(circle);
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

        // Get shape at saved index from model and verify it is a circle
        Shape shape = model.getShape(this.index);
        if (!(shape instanceof cs355.model.drawing.Circle)) {
            GUIFunctions.printf("Invalid shape - expected cs355.model.drawing.Circle at index %d", this.index);
            return;
        }

        // Cast the shape to a circle and save the new coordinates
        Circle circle = (Circle) shape;

        // Initialize new center coordinates to initial coordinates
        Point2D.Double center = new Point2D.Double();
        center.setLocation(this.initialCoordinates);

        // Get current pointer coordinates
        Point2D.Double currentCoordinates = new Point2D.Double();
        currentCoordinates.setLocation(e.getPoint());

        // Find difference between pointer and initial coordinates to get correct orientation
        double xDifference = currentCoordinates.getX() - initialCoordinates.getX();
        double yDifference = currentCoordinates.getY() - initialCoordinates.getY();

        // Get radius
        double radius = Math.min(
                Math.abs(xDifference),
                Math.abs(yDifference)
        ) / 2.0;

        // Get unit vectors for the differences to preserve sign
        double xDirection = xDifference / Math.abs(xDifference);
        double yDirection = yDifference / Math.abs(yDifference);

        // Calculate position of the center of the circle
        center.x = this.initialCoordinates.getX() + (xDirection * radius);
        center.y = this.initialCoordinates.getY() + (yDirection * radius);

        // Set the new parameters
        circle.setCenter(center);
        circle.setRadius(radius);

        // Force the view to refresh now that we have changed the model
        GUIFunctions.refresh();
    }

    @Override
    public void mouseMoved(MouseEvent e, CS355Drawing model, Color c) {
    }

    @Override
    public void close() {

    }
}

