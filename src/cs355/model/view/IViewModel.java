package cs355.model.view;

import cs355.model.IObservable;
import cs355.model.scene.CS355Scene;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Observable;

/**
 * Created by BaronVonBaerenstein on 10/17/2015.
 */
public interface IViewModel extends IObservable {

    /**
     * Updates the frame to match the view model.
     */
    public void updateFrame();

    /**
     * Setter for the center of the view in world coordinates.
     *
     * @param center a point in world coordinates indicating the center of the view window.
     */
    public void setCenter(Point2D.Double center);

    /**
     * Setter for the position of the horizontal scroll bar.
     *
     * @param horizontalScrollPosition the new horizontal scroll position.
     */
    public void setHScrollPosition(int horizontalScrollPosition);

    /**
     * Setter for the position of the vertical scroll bar.
     *
     * @param verticalScrollPosition the new vertical scroll position.
     */
    public void setVScrollPosition(int verticalScrollPosition);

    /**
     * Zooms in on the center of the view.
     */
    public void zoomIn();

    /**
     * Zooms out of the center of the view.
     */
    public void zoomOut();

    /**
     * Gets the numerical factor of the zoom.
     *
     * @return a double representing how much to zoom in or out.
     */
    public double getZoomFactor();

    /**
     * Getter for a transformation from view coordinates to world coordinates.
     *
     * @return an AffineTransform for changing a point from view to world coordinates.
     */
    public AffineTransform getViewToWorld();

    /**
     * Getter for a transformation from world coordinates to view coordinates.
     *
     * @return an AffineTransform for changing a point from world to view coordinates.
     */
    public AffineTransform getWorldToView();

    /**
     * Getter for a transformation matrix from world coordinates to clip coordinates.
     * For 3D rendering.
     *
     * @return a Matrix4D for changing a point from world to clip coordinates.
     */
    public Matrix4D getWorldToClip();

    /**
     * Getter for a transformation matrix from canonical coordinates to screen coordinates.
     * For 3D rendering.
     *
     * @return a Matrix4D for changing a point from canonical coordinates to screen coordinates.
     */
    public Matrix3D getCanonicalToScreen();

    /**
     * Toggles display of the 3D model.
     */
    public void toggle3DModelDisplay();

    /**
     * Says whether the 3D model is being displayed or not.
     *
     * @return true if displayed, otherwise false.
     */
    public boolean is3DModelDisplayed();

    /**
     * Toggles display of the background.
     */
    public void toggleBackgroundDisplay();

    /**
     * Says whether the background image is being displayed or not.
     *
     * @return true if displayed, otherwise false.
     */
    public boolean isBackgroundDisplayed();

}
