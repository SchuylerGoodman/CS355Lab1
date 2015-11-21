package cs355.model.view;

import cs355.model.scene.Point3D;
import cs355.model.scene.Point4D;

/**
 * Created by BaronVonBaerenstein on 11/17/2015.
 */
public class Vector4D {

    public double v0;
    public double v1;
    public double v2;
    public double v3;

    public Vector4D(double v0, double v1, double v2, double v3) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Vector4D() {
        this(0.0, 0.0, 0.0, 0.0);
    }

    public Vector4D(Vector4D vector) {
        this(vector.v0, vector.v1, vector.v2, vector.v3);
    }

    public Vector4D(Point4D point) {
        this(point.x, point.y, point.z, point.w);
    }

    public Vector4D(Point3D point) {
        this(point.x, point.y, point.z, 0.0);
    }

    /**
     * Multiply this vector by another vector.
     *
     * @param right = the vector to multiply this one by.
     * @return = a double with the result of the multiplication.
     */
    public double multiply(Vector4D right) {

        double result = 0.0;

        result += this.v0 * right.v0;
        result += this.v1 * right.v1;
        result += this.v2 * right.v2;
        result += this.v3 * right.v3;

        return result;
    }

    /**
     * Calculates the projection of the point onto a plane.
     *
     * @param normal = the normal of the plane the vector is projected onto.
     * @return = the projection of the point as a Vector4D.
     */
    public Vector4D projectToPlane(Vector4D normal, Vector4D dest) {

        if (dest == null) {
            dest = new Vector4D();
        }

        double dot = this.dot(normal);

        dest.v0 = dot * normal.v0;
        dest.v1 = dot * normal.v1;
        dest.v2 = dot * normal.v2;
        dest.v3 = dot * normal.v3;

        return dest;
    }

    /**
     * Take the dot product of two vectors.
     *
     * @param right = the vector on the right side of the dot product.
     * @return a double representing the magnitude of the projection of the given vectors.
     */
    public double dot(Vector4D right) {

        double dot = this.v0 * right.v0;
        dot += this.v1 * right.v1;
        dot += this.v2 * right.v2;
        dot += this.v3 * right.v3;

        return dot;
    }

    /**
     * Take the cross product of two 3D vectors.
     * This assumes the fourth dimension is a 0.
     *
     * @param right = the vector on the right side of the cross product.
     * @param dest = the vector to store the cross product.
     * @return a Vector4D perpendicular to both given vectors with magnitude ||a|| ||b|| sin(Theta).
     */
    public Vector4D cross(Vector4D right, Vector4D dest) {

        if (dest == null) {
            dest = new Vector4D();
        }

        dest.v0 = ( this.v1 * right.v2 ) - ( this.v2 * right.v1 );
        dest.v1 = -( ( this.v0 * right.v2 ) - ( this.v2 * right.v0 ) );
        dest.v2 = ( this.v0 * right.v1 ) - ( this.v1 * right.v0 );
        dest.v3 = 0.0;

        return dest;
    }

    /**
     * Take the difference of two vectors.
     *
     * @param right = the vector to subtract.
     * @param dest = the vector to store the result.
     * @return a vector pointing from right to this as a Vector4D.
     */
    public Vector4D difference(Vector4D right, Vector4D dest) {

        if (dest == null) {
            dest = new Vector4D();
        }

        dest.v0 = this.v0 - right.v0;
        dest.v1 = this.v1 - right.v1;
        dest.v2 = this.v2 - right.v2;
        dest.v3 = this.v3 - right.v3;

        return dest;
    }

    /**
     * Normalize the given vector.
     *
     * @param dest = the vector to store the unit vector.
     * @return a normalized version of the vector as a Vector4D.
     */
    public Vector4D normalize(Vector4D dest) {

        if (dest == null) {
            dest = new Vector4D();
        }

        double magnitude = this.magnitude();

        dest.v0 = this.v0 / magnitude;
        dest.v1 = this.v1 / magnitude;
        dest.v2 = this.v2 / magnitude;
        dest.v3 = this.v3 / magnitude;

        return dest;
    }

    /**
     * Get the magnitude of the vector.
     *
     * @return the magnitude of the given vector as a double.
     */
    public double magnitude() {

        return Math.sqrt(this.multiply(this));
    }

}