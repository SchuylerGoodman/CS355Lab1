package cs355.view;

import cs355.GUIFunctions;
import cs355.model.scene.*;
import cs355.model.view.IViewModel;
import cs355.model.drawing.*;
import cs355.model.exception.InvalidModelException;
import cs355.model.view.Matrix3D;
import cs355.model.view.Matrix4D;
import cs355.model.view.Vector4D;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created by goodman on 9/8/2015.
 */
public class ViewRefresherImpl implements ViewRefresher {

    /**
     * Model that controls the size and orientation of the view.
     */
    private IViewModel viewModel;

    /**
     * The model for the current drawing.
     */
    private CS355Drawing model;

    /**
     * The model for the 3D scene.
     */
    private IScene scene;

    public ViewRefresherImpl(IViewModel viewModel, CS355Drawing model, IScene scene) {
        this.viewModel = viewModel;
        this.model = model;
        this.scene = scene;
    }

    @Override
    public void refreshView(Graphics2D g2d) {

        // Query the shapes from the model
        List<Shape> shapes = this.model.getShapes();

        // Initialize the factory for the drawables
        DrawableFactory drawableFactory = new DrawableFactory();

        // If we are drawing 3D model, add lines to front of list
        if (this.viewModel.is3DModelDisplayed()) {

            List<Line> lineModels = this.getLineModels();

            shapes.addAll(lineModels);
        }

        // Get an iterator for the shapes in the model
        ListIterator<Shape> iterator = shapes.listIterator();

        while (iterator.hasNext()) {

            // Get the next shape in the model
            Shape s = iterator.next();

            // Try to create the drawable for this shape
            try {
                IDrawable drawable = drawableFactory.create(s);
                drawable.draw(g2d, this.viewModel);
            }
            catch (InvalidModelException e) {
                GUIFunctions.printf(
                        "Shape in model could not be found, or an error was encountered while creating the drawable: \"%s\"",
                        e.getMessage()
                );
            }
        }

    }

    @Override
    public void update(Observable o, Object arg) {

        // Force the view refresh
        GUIFunctions.refresh();
    }

    /**
     * Generate list of lines in screen coordinates for 3D scene.
     *
     * @return list of lines as a List of Line model objects.
     */
    private List<Line> getLineModels() {

        List<IInstance> instances = this.scene.instances();

        List<Line> lineModels = new ArrayList<>();

        // Get World to Clip transform
        Matrix4D worldToClip = this.viewModel.getWorldToClip();

        // Get Canonical to Screen transform
        Matrix3D canonicalToScreen = this.viewModel.getCanonicalToScreen();

        // Convert lines in all instances
        for (IInstance instance : instances) {

            // Get Object to World transform
            Matrix4D objToWorld = instance.getObjectToWorld();

            // Concatenate transforms into Object to Clip transform
            Matrix4D objToClip = worldToClip.concatenate(objToWorld, null);

            // Transform all lines in instance to Line models in screen coordinates
            List<Line3D> modelLines = instance.getModel().getLines();
            List<Line4D> canonicalLines = new ArrayList<>();
            for (Line3D modelLine : modelLines) {
                Line4D modelLine4D = new Line4D(modelLine);
                Line4D clipLine = objToClip.transform(modelLine4D, null);

                // If line is inside clip space, convert to model in screen coordinates and add to list
                if (this.isInsideClip(clipLine)) {
                    //Line4D clippedLine = this.clipLine(clipLine);
                    Line4D canonicalLine4D = clipLine.createCanonical(null);
                    canonicalLines.add(canonicalLine4D);
                }
            }

            // Sort lines by z-value (painter's algorithm)
            Collections.sort(canonicalLines, Collections.reverseOrder());

            // Add lines to model list
            for (Line4D canonicalLine4D : canonicalLines) {

                // Get a vector pointing from start to end
                Vector4D lineAsVector = canonicalLine4D.end.difference(canonicalLine4D.start, null);

                // Create a 3D canonical line with just x, y, w dimensions
                Point3D start = new Point3D(canonicalLine4D.start.x, canonicalLine4D.start.y, canonicalLine4D.start.w);
                Point3D end = new Point3D(lineAsVector.v0, lineAsVector.v1, lineAsVector.v3);
                Line3D canonicalLine = new Line3D(start, end);

                // Transform 3D canonical line to screen coordinates
                Line3D screenLine = canonicalToScreen.transform(canonicalLine, null);

                Line lineModel = new Line(
                        instance.getColor(),
                        new Point2D.Double(screenLine.start.x, screenLine.start.y),
                        new Point2D.Double(screenLine.end.x, screenLine.end.y)
                );

                lineModels.add(lineModel);
            }
        }

        return lineModels;
    }

    private boolean isInsideClip(Line4D line) {

        double startW = Math.abs(line.start.w);
        double nStartW = -1 * startW;
        double endW = Math.abs(line.end.w);
        double nEndW = -1 * endW;

        // If either endpoint fails the near clip test, line is clipped out
        // Near clipping test
        if (line.start.z < nStartW || line.end.z < nEndW) {
            return false;
        }

        // If both endpoints fail the same test, line is clipped out
        // Left clipping test
        if (line.start.x < nStartW && line.end.x < nEndW) {
            return false;
        }

        // Right clipping test
        if (line.start.x > startW && line.end.x > endW) {
            return false;
        }

        // Bottom clipping test
        if (line.start.y < nStartW && line.end.y < nEndW) {
            return false;
        }

        // Top clipping test
        if (line.start.y > startW && line.end.y > endW) {
            return false;
        }

        // Far clipping test
        if (line.start.z > startW && line.end.z > endW) {
            return false;
        }

        return true;

    }

    private Line4D clipLine(Line4D line) {

        // TODO get line to clip inside box, but not get removed completely unless all out of bounds.
        Line4D clipLine = new Line4D(line);

        double startW = Math.abs(line.start.w);
        double nStartW = -1 * startW;
        double endW = Math.abs(line.end.w);
        double nEndW = -1 * endW;


        // Fix x values
        if (clipLine.start.x < nStartW) {
            //clipLine.start.x = nStartW;
            clipLine.start = clipLine.pointAtX(nStartW, null);
        }
        else if (startW < clipLine.start.x) {
            //clipLine.start.x = startW;
            clipLine.start = clipLine.pointAtX(startW, null);
        }

        if (clipLine.end.x < nEndW) {
            //clipLine.end.x = nEndW;
            clipLine.end = clipLine.pointAtX(nEndW, null);
        }
        else if (endW < clipLine.end.x) {
            //clipLine.end.x = endW;
            clipLine.end = clipLine.pointAtX(endW, null);
        }

        // Fix y values
        if (clipLine.start.y < nStartW) {
            //clipLine.start.y = nStartW;
            clipLine.start = clipLine.pointAtY(nStartW, null);
        }
        else if (startW < clipLine.start.y) {
            //clipLine.start.y = startW;
            clipLine.start = clipLine.pointAtY(startW, null);
        }

        if (clipLine.end.y < nEndW) {
            //clipLine.end.y = nEndW;
            clipLine.end = clipLine.pointAtY(nEndW, null);
        }
        else if (endW < clipLine.end.y) {
            //clipLine.end.y = endW;
            clipLine.end = clipLine.pointAtY(endW, null);
        }


        // Fix z values
        if (clipLine.start.z < nStartW) {
            //clipLine.start.z = nStartW;
            clipLine.start = clipLine.pointAtZ(nStartW, null);
        }
        else if (startW < clipLine.start.z) {
            //clipLine.start.z = startW;
            clipLine.start = clipLine.pointAtZ(startW, null);
        }

        if (clipLine.end.z < nEndW) {
            //clipLine.end.z = nEndW
            clipLine.end = clipLine.pointAtZ(nEndW, null);
        }
        else if (endW < clipLine.end.z) {
            //clipLine.end.z = endW;
            clipLine.end = clipLine.pointAtZ(endW, null);
        }



        return clipLine;
    }
}
