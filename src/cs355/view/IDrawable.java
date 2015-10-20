package cs355.view;

import cs355.model.view.AbstractViewModel;

import java.awt.*;

/**
 * Created by goodman on 9/9/2015.
 */
public interface IDrawable {

    /**
     * Method that draws the drawable onto the Graphics2D object.
     *  @param g2d = Graphics2D object on which to draw.
     * @param viewModel = transformation matrix to change world space coordinates to view space.
     */
    void draw(Graphics2D g2d, AbstractViewModel viewModel);
}
