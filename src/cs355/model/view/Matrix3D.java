package cs355.model.view;

import cs355.model.scene.Line3D;
import cs355.model.scene.Point3D;

/**
 * Created by BaronVonBaerenstein on 11/17/2015.
 */
public class Matrix3D {

    public double m00;
    public double m01;
    public double m02;
    public double m10;
    public double m11;
    public double m12;
    public double m20;
    public double m21;
    public double m22;

    public Matrix3D() {

        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
    }

    /**
     * Concatenate this matrix with another into a new matrix.
     *
     * @param right = the matrix to concatenate onto this matrix.
     * @param dest = the matrix to store the result.
     * @return = the Matrix4D with the stored result.
     */
    public Matrix3D concatenate(Matrix3D right, Matrix3D dest) {

        if (dest == null) {
            dest = new Matrix3D();
        }

        dest.m00 = this.m00 * right.m00 + this.m01 * right.m10 + this.m02 * right.m20;
        dest.m01 = this.m00 * right.m01 + this.m01 * right.m11 + this.m02 * right.m21;
        dest.m02 = this.m00 * right.m02 + this.m01 * right.m12 + this.m02 * right.m22;
        dest.m10 = this.m10 * right.m00 + this.m11 * right.m10 + this.m12 * right.m20;
        dest.m11 = this.m10 * right.m01 + this.m11 * right.m11 + this.m12 * right.m21;
        dest.m12 = this.m10 * right.m02 + this.m11 * right.m12 + this.m12 * right.m22;
        dest.m20 = this.m20 * right.m00 + this.m21 * right.m10 + this.m22 * right.m20;
        dest.m21 = this.m20 * right.m01 + this.m21 * right.m11 + this.m22 * right.m21;
        dest.m22 = this.m20 * right.m02 + this.m21 * right.m12 + this.m22 * right.m22;

        return dest;
    }

    /**
     * Transforms a vector using this matrix.
     *
     * @param right = the vector to transform.
     * @param dest = the vector to store the result.
     * @return = the Vector4D with the stored result.
     */
    public Point3D transform(Point3D right, Point3D dest) {

        if (dest == null) {
            dest = new Point3D();
        }

        dest.x = this.m00 * right.x + this.m01 * right.x + this.m02 * right.x;
        dest.y = this.m10 * right.y + this.m11 * right.y + this.m12 * right.y;
        dest.z = this.m20 * right.z + this.m21 * right.z + this.m22 * right.z;

        return dest;
    }

    /**
     * Transforms a line using this matrix.
     *
     * @param right = the line to transform.
     * @param dest = the line to store the result.
     * @return the Line3D with the stored result.
     */
    public Line3D transform(Line3D right, Line3D dest) {

        if (dest == null) {
            dest = new Line3D(new Point3D(), new Point3D());
        }

        dest.start = this.transform(right.start, null);
        dest.end = this.transform(right.end, null);

        return dest;
    }
}
