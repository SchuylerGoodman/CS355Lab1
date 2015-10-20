package cs355.model.drawing.selectable;

import cs355.model.drawing.Circle;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBerenstain on 10/19/2015.
 */
public class RotationHandle extends CircleHandle {

    private static int OFFSET = -16;

    /**
     * Basic constructor that sets fields.
     *
     * @param shape       = the shape that this handle manipulates.
     * @param anchorPoint = the point (in object coordinates) on the reference shape that this handle manipulates.
     * @param center      = the center point of the handle.
     * @param color       = the color of the handle.
     * @param radius      = the radius of the handle in pixels.
     */
    public RotationHandle(Shape shape, Point2D.Double anchorPoint, Point2D.Double center, Color color, double radius) {
        super(shape, anchorPoint, center, color, radius);
    }

    @Override
    public void updateHandle(Point2D.Double anchorPoint, Point2D.Double center, double zoomFactor) {

        super.updateHandle(anchorPoint, center, zoomFactor);

        // Set the new anchor point.
        this.getAnchorPoint().setLocation(anchorPoint);

        // Update the handle shape center
        double minY = this.getReferenceShape().getMinimumY();

        // Normalize the offset so the zoom factor doesn't change the way it looks.
        int offset = (int) ( RotationHandle.OFFSET / this.zoomFactor );

        offset += minY;

        this.getHandleShape().setCenter(new Point2D.Double(0.0, offset));
    }
}
