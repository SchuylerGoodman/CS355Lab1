package cs355.model.scene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BaronVonBaerenstein on 11/19/2015.
 */
public class Axes extends WireFrame {

    private List<Line3D> lines;

    public Axes() {

        lines = new ArrayList<>();

        Line3D forwardAxis = new Line3D(
                new Point3D(0.0, 0.0, 0.0),
                new Point3D(0.0, 0.0, 2.0)
        );

        Line3D rightAxis = new Line3D(
                new Point3D(0.0, 0.0, 0.0),
                new Point3D(2.0, 0.0, 0.0)
        );

        Line3D upAxis = new Line3D(
                new Point3D(0.0, 0.0, 0.0),
                new Point3D(0.0, 2.0, 0.0)
        );

        lines.add(forwardAxis);
        lines.add(rightAxis);
        lines.add(upAxis);
    }

    @Override
    public List<Line3D> getLines() {
        return lines;
    }

}
