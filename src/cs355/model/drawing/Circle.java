package cs355.model.drawing;

import cs355.model.drawing.selectable.CircleHandle;
import cs355.model.drawing.selectable.Handle;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your circle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Circle extends Shape {

	// The radius.
	private double radius;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param radius the radius of the new shape.
	 */
	public Circle(Color color, Point2D.Double center, double radius) {

		// Initialize the superclass.
		super(color, center);

		// Circle has one rotation handle.
		this.setNumHandles(1);

		// Set the field.
		this.radius = radius;
	}

	/**
	 * Getter for this Circle's radius.
	 * @return the radius of this Circle as a double.
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Setter for this Circle's radius.
	 * @param radius the new radius of this Circle.
	 */
	public void setRadius(double radius) {
		this.radius = radius;

		this.setChanged();
		//this.notifyObservers();
	}

	/**
	 * Add your code to do an intersection test
	 * here. You shouldn't need the tolerance.
	 * @param pt = the point to test against (in world coordinates).
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

		double radius = this.getRadius();

		// check with simple bounding box (fast)
		Point2D.Double bound = new Point2D.Double(radius, radius);
		if (Math.abs(ptObj.getX()) > bound.getX() || Math.abs(ptObj.getY()) > bound.getY()) {
			return false;
		}
		//TODO Zoom messes with handle bounding box

		// check if the point is further from center than the radius
		double magnitude = Math.sqrt(Math.pow(ptObj.getX(), 2.0) + Math.pow(ptObj.getY(), 2.0));
		if (magnitude > radius) {
			return false;
		}

		return true;
	}

	@Override
	public void updateHandles(double zoomFactor) {
		for (Handle handle : this.handles) {
			handle.updateHandle(this.getCenter(), this.getCenter(), zoomFactor);
		}
	}

	@Override
	public double getMinimumY() {
		return -1 * radius;
	}

}
