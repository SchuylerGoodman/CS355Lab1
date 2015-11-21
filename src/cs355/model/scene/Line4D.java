package cs355.model.scene;

import cs355.model.view.Vector4D;

import java.util.Comparator;

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

        Point4D start = new Point4D();
        double startW = this.start.w;
        start.x = this.start.x / startW;
        start.y = this.start.y / startW;
        start.z = this.start.z / startW;
        start.w = this.start.w / startW;

        Point4D end = new Point4D();
        double endW = this.end.w;
        end.x = this.end.x / endW;
        end.y = this.end.y / endW;
        end.z = this.end.z / endW;
        end.w = this.end.w / endW;

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
