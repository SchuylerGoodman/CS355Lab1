package cs355.view;

import cs355.model.view.IViewModel;

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
    void draw(Graphics2D g2d, IViewModel viewModel);

    /**
     * Getter to see if the drawable should use transforms when it is drawn.
     * @return true if yes, otherwise no.
     */
    boolean getUseTransforms();

    /**
     * Method that sets whether the drawable should use 2D transforms when drawing.
     * @param useTransforms = whether to use 2D transforms.
     */
    void setUseTransforms(boolean useTransforms);
}
