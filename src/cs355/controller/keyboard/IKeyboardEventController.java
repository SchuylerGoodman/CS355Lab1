package cs355.controller.keyboard;

import java.util.Iterator;

/**
 * Created by BaronVonBaerenstein on 11/17/2015.
 */
public interface IKeyboardEventController {

    /**
     * Handles any key pressed events.
     *
     * @param iterator = an iterator holding the codes of all the keys currently being pressed.
     */
    public void keyPressed(Iterator<Integer> iterator);
}
