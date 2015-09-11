package cs355.view;

import cs355.model.drawing.*;
import cs355.model.drawing.Shape;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by goodman on 9/9/2015.
 */
public class LineDrawable implements IDrawable {

    private Line line;

    public LineDrawable(Shape s) throws InvalidShapeException {
        this.setShape(s);
    }

    @Override
    public void draw(Graphics2D g2d) {
        Line2D drawLine = new Line2D.Double(
                this.line.getStart(),
                this.line.getEnd()
        );

        g2d.setPaint(this.line.getColor());
        g2d.fill(drawLine);
        g2d.draw(drawLine);
    }

    @Override
    public Shape getShape() {
        return line;
    }

    @Override
    public void setShape(Shape s) throws InvalidShapeException {
        if (s instanceof Line) {
            line = (Line) s;
        }
        else {
            line = null;
            throw new InvalidShapeException(
                    String.format(
                            "Cannot convert input shape \"%s\" to cs355.model.drawing.Line",
                            s.getClass().getName()
                    )
            );
        }
    }
}
