package cs355.model.drawing;

import cs355.model.drawing.selectable.CircleHandle;
import cs355.model.drawing.selectable.Handle;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your line code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Line extends Shape {

	// The ending point of the line.
	private Point2D.Double end;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param start the starting point.
	 * @param end the ending point.
	 */
	public Line(Color color, Point2D.Double start, Point2D.Double end) {

		// Initialize the superclass.
		super(color, start);

		// Set the field.
		this.end = end;

        // Line has two handles.
        this.setNumHandles(2);
	}

	/**
	 * Getter for the Line's starting point
	 * which for some inexplicable reason is being saved as "center".
	 * @return the starting point as a Java point.
	 */
	public Point2D.Double getStart() { return this.getCenter(); }

	/**
	 * Setter for this Line's starting point.
	 * @param start the new starting point for the Line.
	 */
	public void setStart(Point2D.Double start) {
		this.center.setLocation(start.getX(), start.getY());
        this.setChanged();
        this.notifyObservers();
	}

	/**
	 * Getter for this Line's ending point.
	 * @return the ending point as a Java point.
	 */
	public Point2D.Double getEnd() {
		return end;
	}

	/**
	 * Setter for this Line's ending point.
	 * @param end the new ending point for the Line.
	 */
	public void setEnd(Point2D.Double end) {
		this.end.setLocation(end.getX(), end.getY());
        this.setChanged();
        this.notifyObservers();
	}

    /**
     * Setter for both of this Line's endpoints.
     * Necessary because if changed individually they will be draw at different times,
     * causing glitching in the interface.
     *
     * @param start = the new starting point for the line, in world coordinates.
     * @param end = the new ending point for the line, in object coordinates.
     */
    public void setEndpoints(Point2D.Double start, Point2D.Double end) {
        this.center.setLocation(start);
        this.end.setLocation(end);
        this.setChanged();
        this.notifyObservers();
    }

    /**
	 * Add your code to do an intersection test
	 * here. You <i>will</i> need the tolerance.
	 * @param pt = the point to test against.
	 * @param tolerance = the allowable tolerance.
	 * @return true if pt is in the shape,
	 *		   false otherwise.
	 */
	@Override
	public boolean pointInShape(Point2D.Double pt, double tolerance) {

        // get the point in object coordinates
        AffineTransform worldToObj = this.getWorldToObj();
        Point2D.Double ptObj = new Point2D.Double();
        worldToObj.transform(pt, ptObj);

        // get the vector for the ray from start to end
        // this is really just the end point, since in object coordinates the start point is (0, 0)
        Point2D.Double lineVector = new Point2D.Double(
                this.getEnd().getX(),
                this.getEnd().getY()
        );

        // calculate the unit normal for the line
        Point2D.Double lineNormal = new Point2D.Double(
                lineVector.getY(),
                lineVector.getX() * -1
        );
        double magnitude = Math.sqrt(Math.pow(lineNormal.getX(), 2.0) + Math.pow(lineNormal.getY(), 2.0));
        lineNormal.setLocation(
                lineNormal.getX() / magnitude,
                lineNormal.getY() / magnitude
        );

        // get the distance from the selected point to the line through the origin
        double ptDistance = Math.abs(
                ptObj.getX() * lineNormal.getX()
                        + ptObj.getY() * lineNormal.getY()
        );

        // if the difference in distance is not within the tolerance, fail
        if (ptDistance > tolerance) {
            return false;
        }

        return true;
	}

    @Override
    public void updateHandles(double zoomFactor) {

        // Update start handle.
        Handle startHandle = this.handles.get(0);
        Point2D.Double newStartAnchorPoint = new Point2D.Double(0.0, 0.0);
        Point2D.Double newStartCenter = new Point2D.Double(this.center.getX(), this.center.getY());
        startHandle.updateHandle(newStartAnchorPoint, newStartCenter, zoomFactor);

        // Update end handle.
        Handle endHandle = this.handles.get(1);
        Point2D.Double newEndAnchorPoint = new Point2D.Double(this.end.getX(), this.end.getY());
        Point2D.Double newEndCenter = new Point2D.Double(
                newStartCenter.getX() + this.end.getX(),
                newStartCenter.getY() + this.end.getY()
        );
        endHandle.updateHandle(newEndAnchorPoint, newEndCenter, zoomFactor);
    }

    @Override
    public double getMinimumY() {
        return Math.min(this.getStart().getY(), this.getEnd().getY());
    }
}
