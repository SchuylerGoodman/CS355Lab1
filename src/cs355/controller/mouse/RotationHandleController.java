package cs355.controller.mouse;

import cs355.model.drawing.*;
import cs355.model.drawing.Shape;
import cs355.model.drawing.selectable.Handle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/2/2015.
 */
public class RotationHandleController extends HandleController {

    private double initialDifference;

    /**
     * Base constructor for handle controllers.
     *
     * @param handle = the handle being controlled.
     */
    public RotationHandleController(Handle handle) {

        super(handle);
        this.initialDifference = 0.0;
    }

    @Override
    public void mouseClicked(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void mousePressed(MouseEvent e, CS355Drawing model, Color c) {

        // Set the initial coordinates of the press
        Point2D.Double initialCoordinates = new Point2D.Double();
        initialCoordinates.setLocation(e.getPoint());

        // Get the shape center and translate the point to object space.
        Shape shape = this.getHandle().getReferenceShape();
        Point2D.Double shapeCenter = shape.getCenter();
        initialCoordinates.setLocation(
                initialCoordinates.getX() - shapeCenter.getX(),
                initialCoordinates.getY() - shapeCenter.getY()
        );

        // Set the initial angle of the press.
        double initialAngle = Math.atan2(initialCoordinates.getY(), initialCoordinates.getX());
        this.initialDifference = initialAngle - shape.getRotation();
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

        // Set the initial coordinates of the drag.
        Point2D.Double dragCoordinates = new Point2D.Double();
        dragCoordinates.setLocation(e.getPoint());

        // Get the shape center and translate the point to object space.
        Shape shape = this.getHandle().getReferenceShape();
        Point2D.Double shapeCenter = shape.getCenter();
        dragCoordinates.setLocation(
                dragCoordinates.getX() - shapeCenter.getX(),
                dragCoordinates.getY() - shapeCenter.getY()
        );

        // Set the initial angle of the drag.
        double dragAngle = Math.atan2(dragCoordinates.getY(), dragCoordinates.getX());
        double targetAngle = dragAngle - this.initialDifference;

        // Set rotation angle of shape.
        shape.setRotation(targetAngle);

    }

    @Override
    public void mouseMoved(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void close() {

    }
}
