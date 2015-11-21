package cs355.controller.mouse;

import cs355.model.drawing.CS355Drawing;
import cs355.model.drawing.Line;
import cs355.model.drawing.selectable.Handle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/2/2015.
 */
public class LineHandleController extends HandleController {

    private Point2D.Double initialCoordinates;

    private Point2D.Double initialAnchor;

    private boolean startHandle;

    /**
     * Base constructor for handle controllers.
     *
     * @param handle = the handle being controlled.
     */
    public LineHandleController(Handle handle) {
        super(handle);

        this.initialCoordinates = new Point2D.Double(0.0, 0.0);
        this.initialAnchor = new Point2D.Double(0.0, 0.0);

        Point2D.Double anchor = handle.getAnchorPoint();
        Line line = (Line) handle.getReferenceShape();
        this.startHandle = true;
        if (anchor.equals(line.getEnd())) {
            this.startHandle = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void mousePressed(MouseEvent e, CS355Drawing model, Color c) {

        this.initialCoordinates.setLocation(e.getPoint());
        if (this.startHandle) {
            Line line = (Line) this.getHandle().getReferenceShape();
            this.initialAnchor.setLocation(
                    this.getHandle().getAnchorPoint().getX() + line.getStart().getX(),
                    this.getHandle().getAnchorPoint().getY() + line.getStart().getY()
            );
        }
        else {
            this.initialAnchor.setLocation(this.getHandle().getAnchorPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void mouseEntered(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void mouseExited(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void mouseDragged(MouseEvent e, CS355Drawing model, Color c) {

        Point2D.Double currentCoordinates = new Point2D.Double();
        currentCoordinates.setLocation(e.getPoint());

        Point2D.Double displacement = new Point2D.Double(
                currentCoordinates.getX() - this.initialCoordinates.getX(),
                currentCoordinates.getY() - this.initialCoordinates.getY()
        );

        Point2D.Double anchor = this.getHandle().getAnchorPoint();
        Point2D.Double targetPoint = new Point2D.Double(
                anchor.getX() + displacement.getX(),
                anchor.getY() + displacement.getY()
        );

        Line line = (Line) this.getHandle().getReferenceShape();
        if (this.startHandle)
        {
            Point2D.Double endPoint = new Point2D.Double(
                    line.getEnd().getX() - displacement.getX(),
                    line.getEnd().getY() - displacement.getY()
            );
            targetPoint.setLocation(
                    targetPoint.getX() + line.getStart().getX(),
                    targetPoint.getY() + line.getStart().getY()
            );
            line.setEndpoints(targetPoint, endPoint);
        }
        else {
            line.setEnd(targetPoint);
        }

        this.initialCoordinates.setLocation(currentCoordinates);
    }

    @Override
    public void mouseMoved(MouseEvent e, CS355Drawing model, Color c) {

    }

    @Override
    public void close() {

    }
}
