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
public class EllipseController implements IController {

    /**
     * The initial coordinates of the mouse press.
     */
    private Point2D.Double initialCoordinates;

    /**
     * The index of the shape in the drawing model.
     */
    private int index;

    public EllipseController() {
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
        Ellipse ellipse = new Ellipse(c, initialCoordinates, 0.0, 0.0);

        // Add line to model and save index
        this.index = model.addShape(ellipse);
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

        // Get shape at saved index from model and verify it is an ellipse
        Shape shape = model.getShape(this.index);
        if (!(shape instanceof cs355.model.drawing.Ellipse)) {
            GUIFunctions.printf("Invalid shape - expected cs355.model.drawing.Ellipse at index %d", this.index);
            return;
        }

        // Cast the shape to a ellipse and save the new coordinates
        Ellipse ellipse = (Ellipse) shape;

        // Initialize new center coordinates to initial coordinates
        Point2D.Double center = new Point2D.Double();
        center.setLocation(this.initialCoordinates);

        // Get current pointer coordinates
        Point2D.Double currentCoordinates = new Point2D.Double();
        currentCoordinates.setLocation(e.getPoint());

        // Find difference between pointer and initial coordinates to get correct orientation
        double xDifference = currentCoordinates.getX() - initialCoordinates.getX();
        double yDifference = currentCoordinates.getY() - initialCoordinates.getY();

        // Calculate position of the center of the ellipse
        center.x = this.initialCoordinates.getX() + (xDifference / 2);
        center.y = this.initialCoordinates.getY() + (yDifference / 2);

        // Get width and height
        double width = Math.abs(xDifference);
        double height = Math.abs(yDifference);

        // Set the new parameters
        ellipse.setCenter(center);
        ellipse.setWidth(width);
        ellipse.setHeight(height);
    }

    @Override
    public void mouseMoved(MouseEvent e, CS355Drawing model, Color c) {
    }

    @Override
    public void close() {

    }
}

