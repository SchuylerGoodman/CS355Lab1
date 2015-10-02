package cs355.controller;

import cs355.model.drawing.CS355Drawing;
import cs355.model.drawing.selectable.Handle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by BaronVonBaerenstein on 10/2/2015.
 */
public abstract class HandleController implements IController {

    private Handle handle;

    private int handleIndex;

    /**
     * Base constructor for handle controllers.
     * @param handle = the handle being controlled.
     * @param handleIndex = the index of the handle in the shape it manipulates.
     */
    public HandleController(Handle handle, int handleIndex) {
        this.handle = handle;
        this.handleIndex = handleIndex;
    }

    /**
     * Getter for the handle begin controlled.
     * @return a handle model.
     */
    public Handle getHandle() {
        return this.handle;
    }

    /**
     * Getter for the index of the handle being controlled int the shape it manipulates.
     * @return the index of the handle.
     */
    public int getHandleIndex() {
        return this.handleIndex;
    }

    @Override
    public abstract void mouseClicked(MouseEvent e, CS355Drawing model, Color c);

    @Override
    public abstract void mousePressed(MouseEvent e, CS355Drawing model, Color c);

    @Override
    public abstract void mouseReleased(MouseEvent e, CS355Drawing model, Color c);

    @Override
    public abstract void mouseEntered(MouseEvent e, CS355Drawing model, Color c);

    @Override
    public abstract void mouseExited(MouseEvent e, CS355Drawing model, Color c);

    @Override
    public abstract void mouseDragged(MouseEvent e, CS355Drawing model, Color c);

    @Override
    public abstract void mouseMoved(MouseEvent e, CS355Drawing model, Color c);

    @Override
    public abstract void close();
}
