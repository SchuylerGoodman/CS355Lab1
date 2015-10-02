package cs355.controller;

import cs355.model.drawing.Line;
import cs355.model.drawing.Shape;
import cs355.model.drawing.selectable.Handle;

/**
 * Created by BaronVonBaerenstein on 10/2/2015.
 */
public class HandleControllerFactory {

    /**
     * Factory method for creating HandleController objects based on the type of handle.
     * @param handle = the handle to control.
     * @param handleIndex = the index of the handle in the shape it manipulates.
     * @return a HandleController object for making the handle work.
     */
    public HandleController create(Handle handle, int handleIndex) {
        Shape shape = handle.getShape();
        if (shape instanceof Line) {
            return new LineHandleController(handle, handleIndex);
        }
        else {
            return new RotationHandleController(handle, handleIndex);
        }
    }
}
