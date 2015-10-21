package cs355.controller;

import cs355.GUIFunctions;
import cs355.model.drawing.CS355Drawing;
import cs355.model.drawing.Shape;
import cs355.model.view.AbstractViewModel;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
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
     * Model that controls the size and orientation of the view.
     */
    private AbstractViewModel viewModel;

    /**
     * The color selected in the UI.
     */
    private Color selectedColor;

    /**
     * The shape controller to use to handle events.
     */
    private IController selectedController;

    public CS355ControllerImpl(CS355Drawing model, AbstractViewModel viewModel, Color c) {
        this.model = model;
        this.viewModel = viewModel;
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
    public void selectButtonHit() { this.setSelectedController(new SelectController(this, this, viewModel)); }

    @Override
    public void zoomInButtonHit() {
        this.viewModel.zoomIn();
        this.updateShapeHandles();
        this.viewModel.updateFrame();
        this.viewModel.notifyObservers();
    }

    @Override
    public void zoomOutButtonHit() {
        this.viewModel.zoomOut();
        this.updateShapeHandles();
        this.viewModel.updateFrame();
        this.viewModel.notifyObservers();
    }

    @Override
    public void hScrollbarChanged(int value) {
        this.viewModel.setHScrollPosition(value);
        this.viewModel.notifyObservers();
    }

    @Override
    public void vScrollbarChanged(int value) {
        this.viewModel.setVScrollPosition(value);
        this.viewModel.notifyObservers();
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
        this.model.notifyObservers();
    }

    @Override
    public void doDeleteShape() {
        int index = this.findSelectedShapeIndex();
        if (index > -1) {
            this.model.deleteShape(index);
            this.model.notifyObservers();
        }
    }

    @Override
    public void doEdgeDetection() {
        this.model.notifyObservers();
    }

    @Override
    public void doSharpen() {
        this.model.notifyObservers();
    }

    @Override
    public void doMedianBlur() {
        this.model.notifyObservers();
    }

    @Override
    public void doUniformBlur() {
        this.model.notifyObservers();
    }

    @Override
    public void doGrayscale() {
        this.model.notifyObservers();
    }

    @Override
    public void doChangeContrast(int contrastAmountNum) {
        this.model.notifyObservers();
    }

    @Override
    public void doChangeBrightness(int brightnessAmountNum) {
        this.model.notifyObservers();
    }

    @Override
    public void doMoveForward() {
        int index = this.findSelectedShapeIndex();
        if (index > -1) {
            this.model.moveForward(index);
            this.model.notifyObservers();
        }
    }

    @Override
    public void doMoveBackward() {
        int index = this.findSelectedShapeIndex();
        if (index > -1) {
            this.model.moveBackward(index);
            this.model.notifyObservers();
        }
    }

    @Override
    public void doSendToFront() {
        int index = this.findSelectedShapeIndex();
        if (index > -1) {
            this.model.moveToFront(index);
            this.model.notifyObservers();
        }
    }

    @Override
    public void doSendtoBack() {
        int index = this.findSelectedShapeIndex();
        if (index > -1) {
            this.model.movetoBack(index);
            this.model.notifyObservers();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.mouseEventToWorldSpace(e);
        this.selectedController.mouseClicked(e, this.model, this.selectedColor);
        this.model.notifyObservers();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.mouseEventToWorldSpace(e);
        this.selectedController.mousePressed(e, this.model, this.selectedColor);
        this.model.notifyObservers();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mouseEventToWorldSpace(e);
        this.selectedController.mouseReleased(e, this.model, this.selectedColor);
        this.model.notifyObservers();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.mouseEventToWorldSpace(e);
        this.selectedController.mouseEntered(e, this.model, this.selectedColor);
        this.model.notifyObservers();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.mouseEventToWorldSpace(e);
        this.selectedController.mouseExited(e, this.model, this.selectedColor);
        this.model.notifyObservers();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mouseEventToWorldSpace(e);
        this.selectedController.mouseDragged(e, this.model, this.selectedColor);
        this.model.notifyObservers();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mouseEventToWorldSpace(e);
        this.selectedController.mouseMoved(e, this.model, this.selectedColor);
        this.model.notifyObservers();
    }

    private void setSelectedController(IController controller) {
        this.selectedController.close();
        this.selectedController = controller;
        this.model.notifyObservers();
    }

    /**
     * Convert a mouse event in place to world coordinates from view coordinates.
     *
     * @param e = mouse event to convert in place.
     */
    private void mouseEventToWorldSpace(MouseEvent e) {

        Point2D mousePoint = new Point2D.Double();
        mousePoint.setLocation(e.getPoint());

        Point2D newPoint = new Point2D.Double();
        this.viewModel.getViewToWorld().transform(mousePoint, newPoint);

        int xDiff = (int) ( newPoint.getX() - mousePoint.getX() );
        int yDiff = (int) ( newPoint.getY() - mousePoint.getY() );

        e.translatePoint(xDiff, yDiff);
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

    /**
     * Updates the handles for all shapes in the model.
     */
    private void updateShapeHandles() {
        for (Shape shape : this.model.getShapes()) {
            shape.updateHandles(this.viewModel.getZoomFactor());
        }
    }
}
