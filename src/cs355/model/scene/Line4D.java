package cs355.model.scene;

import cs355.model.view.Vector4D;

import java.util.Comparator;
import java.util.Vector;

/**
 * Created by BaronVonBaerenstein on 11/17/2015.
 */
public class Line4D implements Comparable<Line4D> {

    public Point4D start;
    public Point4D end;

    public Line4D() {
        this.start = new Point4D();
        this.end = new Point4D();
    }

    public Line4D(Line4D other) {
        this(
                new Point4D(other.start),
                new Point4D(other.end)
        );
    }

    /**
     * Basic constructor.
     *
     * @param start = the new start point of the line.
     * @param end = the new end point of the line.
     */
    public Line4D(Point4D start, Point4D end) {
        this.start = start;
        this.end = end;
    }

    public Line4D(Line3D line) {
        this.start = new Point4D(line.start);
        this.end = new Point4D(line.end);
    }

    public Point4D pointAtX(double x, Point4D dest) {

        if (dest == null) {
            dest = new Point4D();
        }

        Line4D lineCanonical = this.createCanonical(null);
        Point4D start = lineCanonical.start;
        Point4D end = lineCanonical.end;

        Vector4D difference = start.difference(end, null);
        if (difference.v0 == 0.0) {
            return null;
        }

        double t = this.solveT(start.x, difference.v0, x);

        dest.x = x;
        dest.y = start.y + ( t * difference.v1 );
        dest.z = start.z + ( t * difference.v2 );
        dest.w = start.w;

        return dest;
    }

    public Point4D pointAtY(double y, Point4D dest) {

        if (dest == null) {
            dest = new Point4D();
        }

        Line4D lineCanonical = this.createCanonical(null);
        Point4D start = lineCanonical.start;
        Point4D end = lineCanonical.end;

        Vector4D difference = start.difference(end, null);
        if (difference.v1 == 0.0) {
            return null;
        }

        double t = this.solveT(start.y, difference.v1, y);

        dest.x = start.x + ( t * difference.v0 );
        dest.y = y;
        dest.z = start.z + ( t * difference.v2 );
        dest.w = start.w;

        return dest;
    }

    public Point4D pointAtZ(double z, Point4D dest) {

        if (dest == null) {
            dest = new Point4D();
        }

        Line4D lineCanonical = this.createCanonical(null);
        Point4D start = lineCanonical.start;
        Point4D end = lineCanonical.end;

        Vector4D difference = start.difference(end, null);
        if (difference.v2 == 0.0) {
            return null;
        }

        double t = this.solveT(start.z, difference.v2, z);

        dest.x = start.x + ( t * difference.v0 );
        dest.y = start.y + ( t * difference.v1 );
        dest.z = z;
        dest.w = start.w;

        return dest;
    }

    private double solveT(double p0, double p, double pt) {

        double t = ( pt - p0 ) / p;
        return t;
    }

    /**
     * Create a version of this line that corresponds to the canonical view space (w = 1.0)
     *
     * @param dest = the line to store the canonical line.
     * @return a line scaled so w = 1.0 as a Line4D.
     */
    public Line4D createCanonical(Line4D dest) {

        if (dest == null) {
            dest = new Line4D();
        }

        Point4D start = this.start.createCanonical(null);
        Point4D end = this.end.createCanonical(null);

        dest.start = start;
        dest.end = end;

        return dest;

    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Line4D)) {
            return false;
        }

        Line4D other = (Line4D) obj;

        if (this.start.x == other.start.x
                && this.start.y == other.start.y
                && this.start.z == other.start.z
                && this.start.w == other.start.w
                && this.end.x == other.end.x
                && this.end.y == other.end.y
                && this.end.z == other.end.z
                && this.end.w == other.end.w) {
            return true;
        }

        if (this.start.x == other.end.x
                && this.start.y == other.end.y
                && this.start.z == other.end.z
                && this.start.w == other.end.w
                && this.end.x == other.start.x
                && this.end.y == other.start.y
                && this.end.z == other.start.z
                && this.end.w == other.start.w) {
            return true;
        }

        return false;
    }

    @Override
    public int compareTo(Line4D o) {

        // Compare average z values
        double o1AveZ = ( this.start.z + this.end.z ) / 2.0;
        double o2AveZ = ( o.start.z + o.end.z ) / 2.0;
        return Double.compare(o1AveZ, o2AveZ);
    }
}
