package cs355.model.drawing.selectable;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/1/2015.
 */
public interface ISelectable {

    /**
     * Checks if a point in world coordinates is inside this handle.
     *
     * @param pt = the point to check in world coordinates.
     * @param worldToObj = a transformation from world coordinates
     *                   to the coordinates of the object this handle is used to manipulate.
     * @return true if inside
     *          false otherwise
     */
    public boolean pointInShape(Point2D.Double pt, AffineTransform worldToObj);
}
