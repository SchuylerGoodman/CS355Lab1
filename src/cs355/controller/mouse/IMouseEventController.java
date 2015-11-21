package cs355.controller.mouse;

import cs355.model.drawing.CS355Drawing;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Interface for classes that handle mouse events in the controller to draw shapes in the drawing model.
 */
public interface IMouseEventController {

    /**
     * Handles the mouse click event.
     *
     * @param e = the event describing the mouse action
     * @param model = the model on which to draw
     * @param c = the color to draw with
     */
    void mouseClicked(MouseEvent e, CS355Drawing model, Color c);

    /**
     * Handles the mouse pressed event.
     *
     * @param e = the event describing the mouse action
     * @param model = the model on which to draw
     * @param c = the color to draw with
     */
    void mousePressed(MouseEvent e, CS355Drawing model, Color c);

    /**
     * Handles the mouse released event.
     *
     * @param e = the event describing the mouse action
     * @param model = the model on which to draw
     * @param c = the color to draw with
     */
    void mouseReleased(MouseEvent e, CS355Drawing model, Color c);

    /**
     * Handles the mouse entered event.
     *
     * @param e = the event describing the mouse action
     * @param model = the model on which to draw
     * @param c = the color to draw with
     */
    void mouseEntered(MouseEvent e, CS355Drawing model, Color c);

    /**
     * Handles the mouse exited event.
     *
     * @param e = the event describing the mouse action
     * @param model = the model on which to draw
     * @param c = the color to draw with
     */
    void mouseExited(MouseEvent e, CS355Drawing model, Color c);

    /**
     * Handles the mouse dragged event.
     *
     * @param e = the event describing the mouse action
     * @param model = the model on which to draw
     * @param c = the color to draw with
     */
    void mouseDragged(MouseEvent e, CS355Drawing model, Color c);

    /**
     * Handles the mouse moved event.
     *
     * @param e = the event describing the mouse action
     * @param model = the model on which to draw
     * @param c = the color to draw with
     */
    void mouseMoved(MouseEvent e, CS355Drawing model, Color c);

    /**
     * Handles anything that needs to be done when the controller changes.
     */
    void close();
}
