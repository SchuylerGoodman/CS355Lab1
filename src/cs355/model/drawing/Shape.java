package cs355.model.drawing;

import cs355.model.drawing.selectable.CircleHandle;
import cs355.model.drawing.selectable.Handle;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Observable;

/**
 * This is the base class for all of your shapes.
 * Make sure they all extend this class.
 */
public abstract class Shape extends Observable {

    protected static Color HANDLE_COLOR = Color.WHITE;
    protected static double HANDLE_RADIUS = 5.0;
    protected static double HANDLE_OFFSET = 15.0;

	// The color of this shape.
	protected Color color;

	// The center of this shape.
	protected Point2D.Double center;

	// The rotation of this shape.
	protected double rotation;

	// Whether the shape is currently selected.
	protected boolean selected;

	// The handles for manipulating the shape when it is selected.
	protected ArrayList<Handle> handles;

	// The number of handles in the shape.
	protected int numHandles;

	/**
	 * Basic constructor that sets fields.
	 * It initializes rotation to 0.
	 * @param color the color for the new shape.
	 * @param center the center point of the new shape.
	 */
	public Shape(Color color, Point2D.Double center) {
		this.color = color;
		this.center = center;
		rotation = 0.0;
        this.selected = false;
        this.handles = new ArrayList<>();
		this.numHandles = 1;
	}

	/**
	 * Getter for this shape's color.
	 * @return the color of this shape.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for this shape's color
	 * @param color the new color for the shape.
	 */
	public void setColor(Color color) {
		this.color = color;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Getter for this shape's center.
	 * @return this shape's center as a Java point.
	 */
	public Point2D.Double getCenter() {
		return center;
	}

	/**
	 * Setter for this shape's center.
	 * @param center the new center as a Java point.
	 */
	public void setCenter(Point2D.Double center) {
		this.center = center;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Getter for this shape's rotation.
	 * Rotation is interpreted as radians to convert from object coordinates to world coordinates counter-clockwise.
	 * @return the rotation as a double.
	 */
	public double getRotation() {
		return rotation;
	}

	/**
	 * Setter for this shape's rotation.
	 * @param rotation the new rotation.
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Getter for whether the shape has been selected.
	 * @return true if selected.
	 * 			false otherwise.
	 */
	public boolean getSelected() { return selected; }

	/**
	 * Setter for whether the shape has been selected.
	 * @param selected = whether the shape is being selected or deselected.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Getter for the handles for manipulating the shape.
	 *
	 * @return a list of shapes used as handles.
	 */
	public ArrayList<Handle> getHandles() {

		if (this.numHandles	!= this.handles.size())
		{
			this.handles = new ArrayList<>();
			for (int i = 0; i < this.numHandles; ++i) {
				this.handles.add(new CircleHandle(
						this,
                        new Point2D.Double(0.0, 0.0),
                        new Point2D.Double(0.0, 0.0),
                        Shape.HANDLE_COLOR,
                        Shape.HANDLE_RADIUS
				));
			}
		}

		this.updateHandles();
		return this.handles;
	}

	/**
	 * Setter for how many handles this shape should have.
	 * @param numHandles = the integer number of handles for the shape.
	 */
	public void setNumHandles(int numHandles) { this.numHandles = numHandles; }

	/**
	 * Update the handles with new information about it's reference shape.
	 */
	protected abstract void updateHandles();

	/**
	 * Getter for a transformation from world coordinates to this object's coordinates.
	 * @return a transformation as an AffineTransform
	 */
	public AffineTransform getWorldToObj() {

		//AffineTransform worldToObj = new AffineTransform();

		// rotate to neutral orientation
		//worldToObj.rotate(this.getRotation() * -1);
		double rotationFactor = this.getRotation() * -1;
		double m00 = Math.cos(rotationFactor);
		double m11 = Math.cos(rotationFactor);
		double m01 = Math.sin(rotationFactor) * -1;
		double m10 = Math.sin(rotationFactor);

		// translate to the origin
		double x = this.getCenter().getX() * -1;
		double y = this.getCenter().getY() * -1;
		//worldToObj.translate(x, y);

		double m02 = x * m00 + y * m01;
		double m12 = x * m10 + y * m11;

		AffineTransform worldToObj = new AffineTransform(m00, m10, m01, m11, m02, m12);

		return worldToObj;
	}

	/**
	 * Getter for a transformation from this object's coordinates to world coordinates.
	 * @return a transformation as an AffineTransform
	 */
	public AffineTransform getObjToWorld() {
		AffineTransform objToWorld = new AffineTransform();

		// translate to its position in the world
		double x = this.getCenter().getX();
		double y = this.getCenter().getY();
		objToWorld.translate(x, y);

		// rotate to its orientation in the world
		objToWorld.rotate(this.getRotation());

		return objToWorld;
	}

	/**
	 * Used to test for whether the user clicked inside a shape or not.
	 * @param pt = the point to test whether it's in the shape or not.
	 * @param tolerance = the tolerance for testing. Mostly used for lines.
	 * @return true if pt is in the shape, false otherwise.
	 */
	public abstract boolean pointInShape(Point2D.Double pt, double tolerance);

}
