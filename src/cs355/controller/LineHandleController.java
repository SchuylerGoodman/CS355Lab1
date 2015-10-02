package cs355.controller;

import cs355.model.drawing.CS355Drawing;
import cs355.model.drawing.selectable.Handle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by BaronVonBaerenstein on 10/2/2015.
 */
public class LineHandleController extends HandleController {
    /**
     * Base constructor for handle controllers.
     *
     * @param handle      = the handle being controlled.
     * @param handleIndex = the index of the handle in the shape it manipulates.
     */
    public LineHandleController(Handle handle, int handleIndex) {
        super(handle, handleIndex);
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

    }
}
