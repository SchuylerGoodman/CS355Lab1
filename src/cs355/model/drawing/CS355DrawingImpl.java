package cs355.model.drawing;

import cs355.GUIFunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by goodman on 9/8/2015.
 */
public class CS355DrawingImpl extends CS355Drawing {

    private List<Shape> shapes;

    public CS355DrawingImpl() {
        shapes = new ArrayList<>();
    }

    @Override
    public Shape getShape(int index) {
        return this.shapes.get(index);
    }

    @Override
    public int addShape(Shape s) {

        // Initialize failed index
        int index = -1;

        // Try to add the shape
        if (this.shapes.add(s)) {

            // If added, get index of new shape
            index = this.shapes.size() - 1;

            // Notify observers of change
            this.setChanged();
            this.notifyObservers();
        }

        return index;
    }

    @Override
    public void deleteShape(int index) {

        // Try to remove the shape
        try {
            this.shapes.remove(index);

            // Notify observers of removal if successful
            this.setChanged();
            this.notifyObservers();
        }
        catch (IndexOutOfBoundsException e) {
            GUIFunctions.printf("Tried to remove shape from drawing at index %d, index does not exist", index);
        }
    }

    @Override
    public void moveToFront(int index) {

        // Save moving shape temporarily
        Shape tempShape = this.shapes.get(index);

        // Get the front shape in the list
        int frontIndex = this.shapes.size() - 1;
        Shape frontShape = this.shapes.get(frontIndex);

        // Set the front shape to the given index
        this.shapes.set(index, frontShape);

        // Set the temp shape to the front index
        this.shapes.set(frontIndex, tempShape);

        // Notify observers of change
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void movetoBack(int index) {

    }

    @Override
    public void moveForward(int index) {

    }

    @Override
    public void moveBackward(int index) {

    }

    @Override
    public List<Shape> getShapes() {

        // Copy list to preserve internal list structure (does not protect shapes, though)
        List<Shape> shapeCopy = new ArrayList<Shape>(this.shapes);

        // Return copied list
        return shapeCopy;
    }

    @Override
    public List<Shape> getShapesReversed() {

        // Copy list so we don't reverse it permanently
        List<Shape> shapeCopy = new ArrayList<Shape>(this.shapes);

        // Reverse list
        Collections.reverse(shapeCopy);

        // Return reverse copied list
        return shapeCopy;
    }

    @Override
    public void setShapes(List<Shape> shapes) {

        // Replace the old shapes with the new shapes
        this.shapes = shapes;

        // Notify observers of change
        this.setChanged();
        this.notifyObservers();
    }
}
