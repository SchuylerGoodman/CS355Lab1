package cs355.controller;

import cs355.GUIFunctions;
import cs355.model.drawing.CS355Drawing;
import cs355.model.drawing.Shape;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * Created by goodman on 9/8/2015.
 */
public class CS355ControllerImpl extends Observable implements CS355Controller {

    /**
     * The model for the current drawing.
     */
    private CS355Drawing model;

    /**
     * The color selected in the UI.
     */
    private Color selectedColor;

    /**
     * The shape controller to use to handle events.
     */
    private IController selectedController;

    public CS355ControllerImpl(CS355Drawing model, Color c) {
        this.model = model;
        this.selectedColor = c;
        this.selectedController = new NoneController();
    }

    @Override
    public void colorButtonHit(Color c) {
        GUIFunctions.changeSelectedColor(c);
        this.selectedColor = c;
        this.setChanged();
        this.notifyObservers(c);
    }

    @Override
    public void lineButtonHit() {
        this.setSelectedController(new LineController());
    }

    @Override
    public void squareButtonHit() {
        this.setSelectedController(new SquareController());
    }

    @Override
    public void rectangleButtonHit() {
        this.setSelectedController(new RectangleController());
    }

    @Override
    public void circleButtonHit() {
        this.setSelectedController(new CircleController());
    }

    @Override
    public void ellipseButtonHit() {
        this.setSelectedController(new EllipseController());
    }

    @Override
    public void triangleButtonHit() {
        this.setSelectedController(new TriangleController());
    }

    @Override
    public void selectButtonHit() { this.setSelectedController(new SelectController(this, this)); }

    @Override
    public void zoomInButtonHit() {

    }

    @Override
    public void zoomOutButtonHit() {

    }

    @Override
    public void hScrollbarChanged(int value) {

    }

    @Override
    public void vScrollbarChanged(int value) {

    }

    @Override
    public void openScene(File file) {

    }

    @Override
    public void toggle3DModelDisplay() {

    }

    @Override
    public void keyPressed(Iterator<Integer> iterator) {

    }

    @Override
    public void openImage(File file) {

    }

    @Override
    public void saveImage(File file) {

    }

    @Override
    public void toggleBackgroundDisplay() {

    }

    @Override
    public void saveDrawing(File file) {
        this.model.save(file);
    }

    @Override
    public void openDrawing(File file) {
        this.model.open(file);
    }

    @Override
    public void doDeleteShape() {
        int index = this.findSelectedShapeIndex();
        if (index > -1) {
            this.model.deleteShape(index);
        }
    }

    @Override
    public void doEdgeDetection() {

    }

    @Override
    public void doSharpen() {

    }

    @Override
    public void doMedianBlur() {

    }

    @Override
    public void doUniformBlur() {

    }

    @Override
    public void doGrayscale() {

    }

    @Override
    public void doChangeContrast(int contrastAmountNum) {

    }

    @Override
    public void doChangeBrightness(int brightnessAmountNum) {

    }

    @Override
    public void doMoveForward() {
        int index = this.findSelectedShapeIndex();
        if (index > -1) {
            this.model.moveForward(index);
        }
    }

    @Override
    public void doMoveBackward() {
        int index = this.findSelectedShapeIndex();
        if (index > -1) {
            this.model.moveBackward(index);
        }
    }

    @Override
    public void doSendToFront() {
        int index = this.findSelectedShapeIndex();
        if (index > -1) {
            this.model.moveToFront(index);
        }
    }

    @Override
    public void doSendtoBack() {
        int index = this.findSelectedShapeIndex();
        if (index > -1) {
            this.model.movetoBack(index);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.selectedController.mouseClicked(e, this.model, this.selectedColor);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.selectedController.mousePressed(e, this.model, this.selectedColor);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.selectedController.mouseReleased(e, this.model, this.selectedColor);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.selectedController.mouseEntered(e, this.model, this.selectedColor);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.selectedController.mouseExited(e, this.model, this.selectedColor);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.selectedController.mouseDragged(e, this.model, this.selectedColor);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.selectedController.mouseMoved(e, this.model, this.selectedColor);
    }

    private void setSelectedController(IController controller) {
        this.selectedController.close();
        this.selectedController = controller;
    }

    /**
     * Getter for the color selected in the application
     * @return the currently selected color
     */
    public Color getSelectedColor() {
        return this.selectedColor;
    }

    private int findSelectedShapeIndex() {
        if (!(this.selectedController instanceof SelectController)) {
            return -1;
        }

        List<Shape> shapes = this.model.getShapes();
        for (int i = 0; i < shapes.size(); ++i) {
            Shape shape = shapes.get(i);
            if (shape.getSelected()) {
                return i;
            }
        }

        return -1;
    }
}
