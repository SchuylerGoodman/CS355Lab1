package cs355.model.drawing.selectable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Observer;

/**
 * Created by BaronVonBaerenstein on 10/1/2015.
 */
public abstract class Handle implements ISelectable {

    private Point2D.Double center;

    private Color color;

    /**
     * Basic constructor that sets fields.
     *
     * @param center the center point of the new shape.
     */
    public Handle(Point2D.Double center, Color color) {
        this.center = center;
        this.color = color;
    }

    /**
     * Getter for the center point of the handle.
     *
     * @return a point representing the handle in object coordinates
     * relative to the object the handle is used to manipulate.
     */
    public Point2D.Double getCenter() {
        return this.center;
    }

    /**
     * Setter for the center point of the handle in object coordinates
     * relative to the object the handle is used to manipulate.
     *
     * @param center = the new center point.
     */
    public void setCenter(Point2D.Double center) {
        this.center.setLocation(center.getX(), center.getY());
    }

    /**
     * Getter for the color of the handle.
     *
     * @return the color of the handle.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Setter for the color of the handle.
     *
     * @param color = the new color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public abstract boolean pointInShape(Point2D.Double pt, AffineTransform worldToObj);
}
