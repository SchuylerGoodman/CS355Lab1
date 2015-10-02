package cs355.view;

import cs355.GUIFunctions;
import cs355.model.drawing.*;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by goodman on 9/9/2015.
 */
public class LineDrawable extends ShapeDrawable {

    public LineDrawable(Line l) {
        super(l);
    }

    @Override
    public void draw(Graphics2D g2d) {
        Line line = (Line) this.shape;
        Point2D.Double zeroCenter = new Point2D.Double(0.0, 0.0);
        Line2D drawLine = new Line2D.Double(
                zeroCenter,
                line.getEnd()
        );

        g2d.setPaint(line.getColor());
        g2d.setTransform(line.getObjToWorld());
        g2d.fill(drawLine);
        g2d.draw(drawLine);

        super.draw(g2d);
    }
}
