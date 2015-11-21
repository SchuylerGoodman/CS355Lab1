package cs355.model.camera;


import cs355.model.scene.Point4D;
import cs355.model.view.Vector4D;

/**
 * Created by BaronVonBaerenstein on 10/30/2015.
 */
public class Camera implements ICamera {

    private static final Vector4D UP_VECTOR = new Vector4D(0.0, 1.0, 0.0, 0.0);
    private static final Vector4D NEUTRAL_FORWARD = new Vector4D(0.0, 0.0, 1.0, 0.0);

    private Point4D lookFrom;

    private Point4D lookAt;

    private Vector4D upVector;

    public Camera(Point4D initialPosition, double initialRotation) {
        this.init(initialPosition, initialRotation);
    }

    @Override
    public Point4D getLocation() {
        return this.lookFrom;
    }

    @Override
    public double getRotationAngle() {

        // Get the 0 rotation forward axis
        Vector4D startForwardAxis = new Vector4D(NEUTRAL_FORWARD);

        // Get the current forward axis
        Vector4D forwardAxis = this.getForwardAxis();

        // Create a right triangle where the adjacent side is the dot product of the vectors,
        // and the hypotenuse is the start vector
        double dot = startForwardAxis.dot(forwardAxis);
        double startMagnitude = startForwardAxis.magnitude();

        // Take the acos of those sides to get the angle
        // Note that the axis of rotation uses the right-hand rule,
        // so the smallest angle is always the counter-clockwise one.
        double angleRadians = Math.acos(dot / startMagnitude);

        // Find out if the angle is counter-clockwise or clockwise around y
        // If it is clockwise, make it negative
        double sign = startForwardAxis.cross(forwardAxis, null).magnitude();
        if (sign < 0) {
            angleRadians *= -1;
        }

        // Convert radians to degrees
        return Math.toDegrees(angleRadians);
    }

    @Override
    public Vector4D getRotationAxis() {

        /*
        // Get the original forward axis
        Vector4D startForwardAxis = new Vector4D(0.0, 0.0, 1.0);

        // Cross the original forward axis and the current forward axis to get the axis of rotation
        Vector4D rotationAxis = this.cross(startForwardAxis, this.getForwardAxis());

        return rotationAxis;
        */

        // In this lab we can only rotate around y-axis
        return new Vector4D(UP_VECTOR);
    }

    @Override
    public Vector4D getRightAxis() {

        // Get the forward axis
        Vector4D forwardAxis = this.getForwardAxis();

        // Cross the forward axis and up vector to get the right axis
        Vector4D rightAxis = forwardAxis.cross(this.upVector, null);

        // Normalize the right axis
        return rightAxis.normalize(null);
    }

    @Override
    public Vector4D getUpAxis() {

        // Get the right axis
        Vector4D rightAxis = this.getRightAxis();

        // Get the forward axis
        Vector4D forwardAxis = this.getForwardAxis();

        // Cross the right and forward axes to get the up axis
        Vector4D upAxis = rightAxis.cross(forwardAxis, null);

        // Normalize the up axis
        return upAxis.normalize(null);
    }

    @Override
    public Vector4D getForwardAxis() {

        Vector4D forwardAxis = this.lookAt.difference(this.lookFrom, null);

        // Normalize the forward axis
        return forwardAxis.normalize(null);
    }

    @Override
    public void setLocation(Point4D newLocation) {

        // Get the old forward axis
        Vector4D forwardAxis = this.getForwardAxis();

        // Set the new location as lookFrom
        this.lookFrom = newLocation;

        // Reset forward axis, because it sets the lookAt value, and we want that to move with lookFrom
        this.setForwardAxis(forwardAxis);
    }

    @Override
    public void setForwardAxis(Vector4D newForwardAxis) {

        this.lookAt = new Point4D(
                this.lookFrom.x + newForwardAxis.v0,
                this.lookFrom.y + newForwardAxis.v1,
                this.lookFrom.z + newForwardAxis.v2,
                1.0
        );
    }

    /**
     * Sets the camera properties to the given defaults.
     *
     * @param position = new position of the camera in world coordinates.
     * @param rotation = new counter-clockwise rotation of the camera around the y-axis in world coordinates.
     */
    private void init(Point4D position, double rotation) {

        this.lookFrom = position;

        Vector4D forwardAxis = new Vector4D(
                Math.sin(rotation),
                0.0,
                Math.cos(rotation),
                0.0
        );
        this.setForwardAxis(forwardAxis);

        this.upVector = new Vector4D(UP_VECTOR);
    }

}
