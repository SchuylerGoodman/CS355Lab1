package cs355.view;

import cs355.model.drawing.*;
import cs355.model.drawing.Shape;
import cs355.model.drawing.selectable.CircleHandle;
import cs355.model.view.IViewModel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/1/2015.
 */
public class CircleHandleDrawable implements IDrawable {

    /**
     * The handle being drawn.
     */
    private CircleHandle handle;

    private boolean useTransforms;

    /**
     * Constructor for handle drawables.
     * @param h = the handle model.
     */
    public CircleHandleDrawable(CircleHandle h) {
        this.handle = h;
        this.useTransforms = true;
    }

    @Override
    public void draw(Graphics2D g2d, IViewModel viewModel) {

        Circle handleCircle = (Circle) this.handle.getHandleShape();
        Point2D.Double handleCenter = handleCircle.getCenter();

        // Get radius (needs to be the same at all zoom levels
        double radius = handleCircle.getRadius() / viewModel.getZoomFactor();

        // Calculate upper left corner of bounding rectangle
        Point2D.Double upperLeftCorner = new Point2D.Double();
        upperLeftCorner.setLocation(handleCenter.getX() - radius, handleCenter.getY() - radius);

        // Initialize circle geometric object
        Ellipse2D drawCircle = new Ellipse2D.Double(
                upperLeftCorner.getX(),
                upperLeftCorner.getY(),
                radius * 2,
                radius * 2
        );

        // Concatenate objToWorld and worldToView transforms to get objToView
        Shape referenceShape = this.handle.getReferenceShape();
        AffineTransform objToView = new AffineTransform(viewModel.getWorldToView());
        objToView.concatenate(referenceShape.getObjToWorld());
        //objToView.concatenate(offsetTransform);

        // Regulate the stroke, so the width of the border doesn't change with the zoom level
        // and keep the width of the circle line at 1.
        float strokeWidth = 1 / (float) viewModel.getZoomFactor();
        g2d.setStroke(new BasicStroke(strokeWidth));

        g2d.setPaint(handleCircle.getColor());
        g2d.setTransform(objToView);
        g2d.draw(drawCircle);
    }

    @Override
    public void setUseTransforms(boolean useTransforms) {
        this.useTransforms = useTransforms;
    }

    @Override
    public boolean getUseTransforms() {
        return this.useTransforms;
    }
}
