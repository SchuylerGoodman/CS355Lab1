package cs355.model.drawing.selectable;

import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Observer;

/**
 * Created by BaronVonBaerenstein on 10/1/2015.
 */
public abstract class Handle implements ISelectable {

    protected static Color HANDLE_COLOR = Color.WHITE;

    private Shape referenceShape;

    private Shape handleShape;

    private Point2D.Double anchorPoint;

    protected double zoomFactor;

    /**
     * Basic constructor that sets fields.
     *
     * @param referenceShape the shape this handle manipulates.
     */
    public Handle(Shape referenceShape, Point2D.Double anchorPoint) {
        this.referenceShape = referenceShape;
        this.handleShape = null;
        this.anchorPoint = anchorPoint;
        this.zoomFactor = 1.0;
    }

    /**
     * Getter for the shape this handle manipulates.
     *
     * @return the shape model this handle manipulates.
     */
    public Shape getReferenceShape() { return this.referenceShape; }

    /**
     * Getter for the shape of the handle.
     *
     * @return the shape model that represents the "physical" location of the handle.
     */
    public Shape getHandleShape() { return this.handleShape; }

    /**
     * Getter for the point this handle is anchored to in the shape
     *
     * @return a Point2D with the coordinates of the anchor point in object space.
     */
    public Point2D.Double getAnchorPoint() { return this.anchorPoint; }

    /**
     * Setter for the shape of the handle.
     *
     * @param handleShape a model to represent the "physical" location of the handle.
     */
    protected void setHandleShape(Shape handleShape) { this.handleShape = handleShape; }

    public void updateHandle(Point2D.Double anchorPoint, Point2D.Double center, double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

}
