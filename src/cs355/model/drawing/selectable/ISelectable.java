package cs355.model.drawing.selectable;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/1/2015.
 */
public interface ISelectable {

    /**
     * Checks if a point in world coordinates is inside this selectable.
     *
     * @param pt = the point to check in world coordinates.
     * @param tolerance = the amount of tolerance in pixels allowed around the selectable to successfully select it.
     * @return true if inside
     *          false otherwise
     */
    public boolean pointInside(Point2D.Double pt, double tolerance);
}
