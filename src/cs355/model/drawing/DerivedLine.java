package cs355.model.drawing;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by goodman on 11/23/2015.
 */
public class DerivedLine extends Line implements I3DDerived {
    /**
     * Basic constructor that sets all fields.
     *
     * @param color the color for the new shape.
     * @param start the starting point.
     * @param end   the ending point.
     */
    public DerivedLine(Color color, Point2D.Double start, Point2D.Double end) {
        super(color, start, end);
    }
}
