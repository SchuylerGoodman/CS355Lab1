package cs355.model.view;

import cs355.model.scene.Line4D;
import cs355.model.scene.Point4D;

import java.awt.*;

/**
 * Created by BaronVonBaerenstein on 11/16/2015.
 */
public class Matrix4D {

    public double m00;
    public double m01;
    public double m02;
    public double m03;
    public double m10;
    public double m11;
    public double m12;
    public double m13;
    public double m20;
    public double m21;
    public double m22;
    public double m23;
    public double m30;
    public double m31;
    public double m32;
    public double m33;

    public Matrix4D() {
        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    /**
     * Concatenate this matrix with another into a new matrix.
     *
     * @param right = the matrix to concatenate onto this matrix.
     * @param dest = the matrix to store the result.
     * @return = the Matrix4D with the stored result.
     */
    public Matrix4D concatenate(Matrix4D right, Matrix4D dest) {

        if (dest == null) {
            dest = new Matrix4D();
        }

        dest.m00 = this.m00 * right.m00 + this.m01 * right.m10 + this.m02 * right.m20 + this.m03 * right.m30;
        dest.m01 = this.m00 * right.m01 + this.m01 * right.m11 + this.m02 * right.m21 + this.m03 * right.m31;
        dest.m02 = this.m00 * right.m02 + this.m01 * right.m12 + this.m02 * right.m22 + this.m03 * right.m32;
        dest.m03 = this.m00 * right.m03 + this.m01 * right.m13 + this.m02 * right.m23 + this.m03 * right.m33;
        dest.m10 = this.m10 * right.m00 + this.m11 * right.m10 + this.m12 * right.m20 + this.m13 * right.m30;
        dest.m11 = this.m10 * right.m01 + this.m11 * right.m11 + this.m12 * right.m21 + this.m13 * right.m31;
        dest.m12 = this.m10 * right.m02 + this.m11 * right.m12 + this.m12 * right.m22 + this.m13 * right.m32;
        dest.m13 = this.m10 * right.m03 + this.m11 * right.m13 + this.m12 * right.m23 + this.m13 * right.m33;
        dest.m20 = this.m20 * right.m00 + this.m21 * right.m10 + this.m22 * right.m20 + this.m23 * right.m30;
        dest.m21 = this.m20 * right.m01 + this.m21 * right.m11 + this.m22 * right.m21 + this.m23 * right.m31;
        dest.m22 = this.m20 * right.m02 + this.m21 * right.m12 + this.m22 * right.m22 + this.m23 * right.m32;
        dest.m23 = this.m20 * right.m03 + this.m21 * right.m13 + this.m22 * right.m23 + this.m23 * right.m33;
        dest.m30 = this.m30 * right.m00 + this.m31 * right.m10 + this.m32 * right.m20 + this.m33 * right.m30;
        dest.m31 = this.m30 * right.m01 + this.m31 * right.m11 + this.m32 * right.m21 + this.m33 * right.m31;
        dest.m32 = this.m30 * right.m02 + this.m31 * right.m12 + this.m32 * right.m22 + this.m33 * right.m32;
        dest.m33 = this.m30 * right.m03 + this.m31 * right.m13 + this.m32 * right.m23 + this.m33 * right.m33;

        return dest;
    }

    /**
     * Transforms a vector using this matrix.
     *
     * @param right = the vector to transform.
     * @param dest = the vector to store the result.
     * @return the Vector4D with the stored result.
     */
    public Vector4D transform(Vector4D right, Vector4D dest) {

        if (dest == null) {
            dest = new Vector4D();
        }

        dest.v0 = this.m00 * right.v0 + this.m01 * right.v1 + this.m02 * right.v2 + this.m03 * right.v3;
        dest.v1 = this.m10 * right.v0 + this.m11 * right.v1 + this.m12 * right.v2 + this.m13 * right.v3;
        dest.v2 = this.m20 * right.v0 + this.m21 * right.v1 + this.m22 * right.v2 + this.m23 * right.v3;
        dest.v3 = this.m30 * right.v0 + this.m31 * right.v1 + this.m32 * right.v2 + this.m33 * right.v3;

        return dest;
    }

    /**
     * Transforms a point using this matrix.
     *
     * @param right = the point to transform.
     * @param dest = the point to store the result.
     * @return the Point4D with the stored result.
     */
    public Point4D transform(Point4D right, Point4D dest) {

        if (dest == null) {
            dest = new Point4D();
        }

        dest.x = this.m00 * right.x + this.m01 * right.y + this.m02 * right.z + this.m03 * right.w;
        dest.y = this.m10 * right.x + this.m11 * right.y + this.m12 * right.z + this.m13 * right.w;
        dest.z = this.m20 * right.x + this.m21 * right.y + this.m22 * right.z + this.m23 * right.w;
        dest.w = this.m30 * right.x + this.m31 * right.y + this.m32 * right.z + this.m33 * right.w;

        return dest;
    }

    /**
     * Transforms a line using this matrix.
     *
     * @param right = the line to transform.
     * @param dest = the line to store the result.
     * @return the Line4D with the stored result.
     */
    public Line4D transform(Line4D right, Line4D dest) {

        if (dest == null) {
            dest = new Line4D();
        }

        dest.start = this.transform(right.start, null);
        dest.end = this.transform(right.end, null);

        return dest;
    }

}
