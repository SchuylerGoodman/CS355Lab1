package cs355.model.view;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Observable;

/**
 * Created by BaronVonBaerenstein on 10/17/2015.
 */
public abstract class AbstractViewModel extends Observable {

    /**
     * Setter for the center of the view in world coordinates.
     *
     * @param center a point in world coordinates indicating the center of the view window.
     */
    public abstract void setCenter(Point2D.Double center);

    /**
     * Setter for the position of the horizontal scroll bar.
     *
     * @param horizontalScrollPosition the new horizontal scroll position.
     */
    public abstract void setHScrollPosition(int horizontalScrollPosition);

    /**
     * Setter for the position of the vertical scroll bar.
     *
     * @param verticalScrollPosition the new vertical scroll position.
     */
    public abstract void setVScrollPosition(int verticalScrollPosition);

    /**
     * Zooms in on the center of the view.
     */
    public abstract void zoomIn();

    /**
     * Zooms out of the center of the view.
     */
    public abstract void zoomOut();

    /**
     * Gets the numerical factor of the zoom.
     *
     * @return a double with a value in {0.25, 0.5, 1.0, 2.0, 4.0}
     */
    public abstract double getZoomFactor();

    /**
     * Getter for a transformation from view coordinates to world coordinates.
     *
     * @return an AffineTransform for changing a point from view to world coordinates.
     */
    public abstract AffineTransform getViewToWorld();

    /**
     * Getter for a transformation from world coordinates to view coordinates.
     *
     * @return an AffineTransform for changing a point from world to view coordinates.
     */
    public abstract AffineTransform getWorldToView();

}
