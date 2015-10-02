package cs355.model.drawing;

import cs355.model.drawing.selectable.CircleHandle;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your rectangle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Rectangle extends Shape {

	// The width of this shape.
	private double width;

	// The height of this shape.
	private double height;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param width the width of the new shape.
	 * @param height the height of the new shape.
	 */
	public Rectangle(Color color, Point2D.Double center, double width, double height) {

		// Initialize the superclass.
		super(color, center);

		// Set fields.
		this.width = width;
		this.height = height;

        // Initialize the handles.
        CircleHandle handleStart = new CircleHandle(
                new Point2D.Double(0.0, (-1 * this.height / 2) - Shape.HANDLE_OFFSET),
                Shape.HANDLE_COLOR,
                Shape.HANDLE_RADIUS
        );
        this.handles.add(handleStart);
	}

	/**
	 * Getter for this shape's width.
	 * @return this shape's width as a double.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Setter for this shape's width.
	 * @param width the new width.
	 */
	public void setWidth(double width) {
		this.width = width;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Getter for this shape's height.
	 * @return this shape's height as a double.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Setter for this shape's height.
	 * @param height the new height.
	 */
	public void setHeight(double height) {
		this.height = height;
		this.handles.get(0).setCenter(
				new Point2D.Double(0.0, (-1 * this.height / 2) - Shape.HANDLE_OFFSET)
		);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Add your code to do an intersection test
	 * here. You shouldn't need the tolerance.
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

		double halfWidth = this.getWidth() / 2.0;
		double halfHeight = this.getHeight() / 2.0;

		// check with simple bounding box (fast)
		Point2D.Double bound = new Point2D.Double(halfWidth, halfHeight);
		if (Math.abs(ptObj.getX()) > bound.getX() || Math.abs(ptObj.getY()) > bound.getY()) {
			return false;
		}

		return true;
	}

}
