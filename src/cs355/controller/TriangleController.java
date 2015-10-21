package cs355.controller;

import cs355.model.drawing.CS355Drawing;
import cs355.model.drawing.Triangle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Class that controls how a triangle is drawn.
 */
public class TriangleController implements IController {

    private static int MAX_POINTS = 3;

    private Point2D.Double firstCoordinates;
    private Point2D.Double secondCoordinates;
    private Point2D.Double thirdCoordinates;
    private int currentPoint;

    public TriangleController() {
        firstCoordinates = new Point2D.Double();
        secondCoordinates = new Point2D.Double();
        thirdCoordinates = new Point2D.Double();
        currentPoint = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e, CS355Drawing model, Color c) {

        if (currentPoint > 2) {
            currentPoint = 0;
        }

        if (currentPoint == 0) {

            // First click, first corner
            firstCoordinates.setLocation(e.getPoint());
        }
        else if (currentPoint == 1) {

            // Second click, second corner
            secondCoordinates.setLocation(e.getPoint());
        }
        else {

            // Third click, last corner
            thirdCoordinates.setLocation(e.getPoint());

            // Calculate centroid
            double xAve = (firstCoordinates.getX() + secondCoordinates.getX() + thirdCoordinates.getX()) / 3;
            double yAve = (firstCoordinates.getY() + secondCoordinates.getY() + thirdCoordinates.getY()) / 3;
            Point2D.Double center = new Point2D.Double(xAve, yAve);

            // Copy coordinates
            Point2D.Double firstCopy = copyPoint(firstCoordinates);
            Point2D.Double secondCopy = copyPoint(secondCoordinates);
            Point2D.Double thirdCopy = copyPoint(thirdCoordinates);

            // Initialize the triangle
            Triangle triangle = new Triangle(
                    c,
                    center,
                    firstCopy,
                    secondCopy,
                    thirdCopy
            );

            // Transform triangle points from world to object coordinates
            AffineTransform worldToObj = triangle.getWorldToObj();
            worldToObj.transform(firstCopy, firstCopy);
            worldToObj.transform(secondCopy, secondCopy);
            worldToObj.transform(thirdCopy, thirdCopy);

            // These have already changed, because worldToObj transforms in place
            // but the handle needs to be updated, so this is how it will happen for now.
            triangle.setA(firstCopy);
            triangle.setB(secondCopy);
            triangle.setC(thirdCopy);

            // Add triangle to model
            model.addShape(triangle);
        }

        ++currentPoint;
    }

    @Override
    public void mousePressed(MouseEvent e, CS355Drawing model, Color c) {
    }

    @Override
    public void mouseReleased(MouseEvent e, CS355Drawing model, Color c) {
    }

    @Override
    public void mouseEntered(MouseEvent e, CS355Drawing model, Color c) {
    }

    @Override
    public void mouseExited(MouseEvent e, CS355Drawing model, Color c) {
    }

    @Override
    public void mouseDragged(MouseEvent e, CS355Drawing model, Color c) {
    }

    @Override
    public void mouseMoved(MouseEvent e, CS355Drawing model, Color c) {
    }

    @Override
    public void close() {

    }

    private Point2D.Double copyPoint(Point2D.Double point) {

        Point2D.Double newPoint = new Point2D.Double();
        newPoint.setLocation(point);

        return newPoint;
    }
}
