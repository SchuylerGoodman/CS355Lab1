package cs355.model.scene;

import cs355.model.view.Vector4D;

/**
 * Created by BaronVonBaerenstein on 11/19/2015.
 */
public class Point4D {

    // Coordinates as doubles.
    public double x;
    public double y;
    public double z;
    public double w;

    /**
     * Basic constructor. Puts the Point at the origin.
     */
    public Point4D() {
        this(0.0, 0.0, 0.0, 1.0);
    }

    /**
     * Basic constructor.
     * @param x = the x coordinate of the new point.
     * @param y = the y coordinate of the new point.
     * @param z = the z coordinate of the new point.
     * @param w
     */
    public Point4D(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Point4D(Point4D other) {
        this(other.x, other.y, other.z, other.w);
    }

    public Point4D(Point3D point) {
        this(point.x, point.y, point.z, 1.0);
    }

    public Point4D(Vector4D vector) {
        this(
                vector.v0,
                vector.v1,
                vector.v2,
                1.0
        );
    }

    /**
     * Get the vector from a point to this point.
     *
     * @param right = the origin of the vector.
     * @param dest = the vector to store the result.
     * @return a Vector4D pointing from right to this.
     */
    public Vector4D difference(Point4D right, Vector4D dest) {

        if (dest == null) {
            dest = new Vector4D();
        }

        dest.v0 = this.x - right.x;
        dest.v1 = this.y - right.y;
        dest.v2 = this.z - right.z;
        dest.v3 = this.w - right.w;

        return dest;
    }

    public Point4D createCanonical(Point4D dest) {

        if (dest == null) {
            dest = new Point4D();
        }

        dest.x = this.x / this.w;
        dest.y = this.y / this.w;
        dest.z = this.y / this.w;
        dest.w = this.w / this.w;

        return dest;
    }
}
