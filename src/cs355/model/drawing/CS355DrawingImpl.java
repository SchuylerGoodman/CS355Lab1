package cs355.model.drawing;

import cs355.GUIFunctions;

import java.util.*;

/**
 * Created by goodman on 9/8/2015.
 */
public class CS355DrawingImpl extends CS355Drawing implements Observer {

    private ArrayList<Shape> shapes;

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

            // Observe changes in the shape
            this.shapes.get(index).addObserver(this);

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

        int listSize = this.shapes.size();
        if (index > -1 && index < listSize - 1) {
            for (int shapeIndex = index; shapeIndex < listSize; ++shapeIndex) {
                this.moveForward(shapeIndex);
            }

            this.setChanged();
            this.notifyObservers();
        }
    }

    @Override
    public void movetoBack(int index) {

        int listSize = this.shapes.size();
        if (index > 0 && index < listSize) {
            for (int shapeIndex = index; shapeIndex > 0; --shapeIndex) {
                this.moveBackward(shapeIndex);
            }

            this.setChanged();
            this.notifyObservers();
        }
    }

    @Override
    public void moveForward(int index) {

        int nextIndex = index + 1;
        if (nextIndex < this.shapes.size()) {
            Collections.swap(this.shapes, index, nextIndex);
            this.setChanged();
            this.notifyObservers();
        }
    }

    @Override
    public void moveBackward(int index) {

        int lastIndex = index - 1;
        if (lastIndex > -1) {
            Collections.swap(this.shapes, lastIndex, index);
            this.setChanged();
            this.notifyObservers();
        }
    }

    @Override
    public List<Shape> getShapes() {

        // Copy list to preserve internal list structure (does not protect shapes, though)
        List<Shape> shapeCopy = new ArrayList<>(this.shapes);

        // Return copied list
        return shapeCopy;
    }

    @Override
    public List<Shape> getShapesReversed() {

        // Copy list so we don't reverse it permanently
        List<Shape> shapeCopy = new ArrayList<>(this.shapes);

        // Reverse list
        Collections.reverse(shapeCopy);

        // Return reverse copied list
        return shapeCopy;
    }

    @Override
    public void setShapes(List<Shape> shapes) {

        // Replace the old shapes with the new shapes
        this.shapes = new ArrayList<>(shapes);

        // Notify observers of change
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void update(Observable o, Object arg) {

        // Notify observers of change
        this.setChanged();
        this.notifyObservers();
    }
}
