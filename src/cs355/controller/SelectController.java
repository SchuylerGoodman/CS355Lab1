package cs355.controller;

import cs355.model.drawing.*;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Class that handles input for selecting and manipulating objects.
 */
public class SelectController implements IController {

    /**
     * The shape which has been selected.
     */
    private Shape selectedShape;

    public SelectController() {
        this.selectedShape = null;
    }

    @Override
    public void mouseClicked(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void mousePressed(MouseEvent e, CS355Drawing model, Color c) {

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
    }
}
