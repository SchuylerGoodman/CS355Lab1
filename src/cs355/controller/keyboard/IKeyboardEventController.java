package cs355.controller.keyboard;

import java.util.Iterator;

/**
 * Created by BaronVonBaerenstein on 11/17/2015.
 */
public interface IKeyboardEventController {

    /**
     * Handles any key pressed events.
     *
     * @param iterator = I have no idea how this works yet TODO figure out how key pressed works
     */
    public void keyPressed(Iterator<Integer> iterator);
}
