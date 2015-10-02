package cs355.model.drawing;

import cs355.model.drawing.selectable.CircleHandle;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your triangle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Triangle extends Shape {

	// The three points of the triangle.
	private Point2D.Double a;
	private Point2D.Double b;
	private Point2D.Double c;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param a the first point, relative to the center.
	 * @param b the second point, relative to the center.
	 * @param c the third point, relative to the center.
	 */
	public Triangle(Color color, Point2D.Double center, Point2D.Double a,
					Point2D.Double b, Point2D.Double c)
	{

		// Initialize the superclass.
		super(color, center);

		// Set fields.
		this.a = a;
		this.b = b;
		this.c = c;

		// Initialize the handles.
		double minY = this.getMinimumYCoordinate();
		CircleHandle handleStart = new CircleHandle(
				new Point2D.Double(0.0, minY - Shape.HANDLE_OFFSET),
				Shape.HANDLE_COLOR,
				Shape.HANDLE_RADIUS
		);
		this.handles.add(handleStart);
	}

	/**
	 * Getter for the first point.
	 * @return the first point as a Java point.
	 */
	public Point2D.Double getA() {
		return a;
	}

	/**
	 * Setter for the first point.
	 * @param a the new first point.
	 */
	public void setA(Point2D.Double a) {
		this.a = a;
		double minY = this.getMinimumYCoordinate();
		this.handles.get(0).setCenter(
				new Point2D.Double(0.0, minY - Shape.HANDLE_OFFSET)
		);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Getter for the second point.
	 * @return the second point as a Java point.
	 */
	public Point2D.Double getB() {
		return b;
	}

	/**
	 * Setter for the second point.
	 * @param b the new second point.
	 */
	public void setB(Point2D.Double b) {
		this.b = b;
		double minY = this.getMinimumYCoordinate();
		this.handles.get(0).setCenter(
				new Point2D.Double(0.0, minY - Shape.HANDLE_OFFSET)
		);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Getter for the third point.
	 * @return the third point as a Java point.
	 */
	public Point2D.Double getC() {
		return c;
	}

	/**
	 * Setter for the third point.
	 * @param c the new third point.
	 */
	public void setC(Point2D.Double c) {
		this.c = c;
		double minY = this.getMinimumYCoordinate();
		this.handles.get(0).setCenter(
				new Point2D.Double(0.0, minY - Shape.HANDLE_OFFSET)
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

		// get the point in object coordinates.
		AffineTransform worldToObj = this.getWorldToObj();
		Point2D.Double ptObj = new Point2D.Double();
		worldToObj.transform(pt, ptObj);

		// get orthogonal vectors for triangle lines
		Point2D.Double orthogonalAB = new Point2D.Double(
				this.getB().getY() - this.getA().getY(),
				(this.getB().getX() - this.getA().getX()) * -1
		);
		Point2D.Double orthogonalBC = new Point2D.Double(
				this.getC().getY() - this.getB().getY(),
				(this.getC().getX() - this.getB().getX()) * -1
		);
		Point2D.Double orthogonalCA = new Point2D.Double(
				this.getA().getY() - this.getC().getY(),
				(this.getA().getX() - this.getC().getX()) * -1
		);

		// get lines from each corner to the selected point
		Point2D.Double lineAPt = new Point2D.Double(
				ptObj.getX() - this.getA().getX(),
				ptObj.getY() - this.getA().getY()
		);
		Point2D.Double lineBPt = new Point2D.Double(
				ptObj.getX() - this.getB().getX(),
				ptObj.getY() - this.getB().getY()
		);
		Point2D.Double lineCPt = new Point2D.Double(
				ptObj.getX() - this.getC().getX(),
				ptObj.getY() - this.getC().getY()
		);

		// see if lineT_1Pt and orthogonalT_1T_2 are pointing the same direction
		double signAPt = lineAPt.getX() * orthogonalAB.getX() + lineAPt.getY() * orthogonalAB.getY();
		double signBPt = lineBPt.getX() * orthogonalBC.getX() + lineBPt.getY() * orthogonalBC.getY();
		double signCPt = lineCPt.getX() * orthogonalCA.getX() + lineCPt.getY() * orthogonalCA.getY();

		// bound the sign so we get -1, 0, or 1
		signAPt = signAPt > 0 ? 1 : signAPt == 0 ? 0 : -1;
		signBPt = signBPt > 0 ? 1 : signBPt == 0 ? 0 : -1;
		signCPt = signCPt > 0 ? 1 : signCPt == 0 ? 0 : -1;

		// 0 means its on the line, and I say that's within the bounds
		// So if the signs for any two adjacent lines are not equal, and neither is 0,
		// then the point is outside the triangle.
		if (
				(signAPt != signBPt && signAPt != 0 && signBPt != 0) ||
				(signBPt != signCPt && signBPt != 0 && signCPt != 0) ||
				(signCPt != signAPt && signCPt != 0 && signAPt != 0))
		{
			return false;
		}

		return true;
	}

	/**
	 * Get the minimum Y coordinate relative to the center of the triangle.
	 * @return the minimum Y coordinate as a double.
	 */
	private double getMinimumYCoordinate() {

		double min = Math.min(this.getA().getY(), this.getB().getY());
		min = Math.min(min, this.getC().getY());

		System.out.println("A: " + this.getA().getY());
		System.out.println("B: " + this.getB().getY());
		System.out.println("C: " + this.getC().getY());
		System.out.println("max: " + min);

		return min;
	}

}
