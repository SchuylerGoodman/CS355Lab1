package cs355.view;

import cs355.model.drawing.*;
import cs355.model.view.AbstractViewModel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Created by goodman on 9/9/2015.
 */
public class LineDrawable extends ShapeDrawable {

    public LineDrawable(Line l) {
        super(l);
    }

    @Override
    public void draw(Graphics2D g2d, AbstractViewModel viewModel) {

        super.draw(g2d, viewModel);

        Line line = (Line) this.shape;
        Point2D.Double zeroCenter = new Point2D.Double(0.0, 0.0);
        Line2D drawLine = new Line2D.Double(
                zeroCenter,
                line.getEnd()
        );

        // Concatenate objToWorld and worldToView transforms to get objToView
        AffineTransform objToView = new AffineTransform(viewModel.getWorldToView());
        objToView.concatenate(line.getObjToWorld());

        // Regulate the stroke, so the width of the border doesn't change with the zoom level
        // and so the line has width 1
        float strokeWidth = 1 / (float) viewModel.getZoomFactor();
        g2d.setStroke(new BasicStroke(strokeWidth));

        g2d.setPaint(line.getColor());
        g2d.setTransform(objToView);
        g2d.fill(drawLine);
        g2d.draw(drawLine);
    }
}
