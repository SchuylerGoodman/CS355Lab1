package cs355.controller;

import cs355.GUIFunctions;
import cs355.model.drawing.CS355Drawing;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Iterator;

/**
 * Created by goodman on 9/8/2015.
 */
public class CS355ControllerImpl implements CS355Controller {

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
    private IShapeController selectedShapeController;

    public CS355ControllerImpl(CS355Drawing model, Color c) {
        this.model = model;
        this.selectedColor = c;
        this.selectedShapeController = new NoneController();
    }

    @Override
    public void colorButtonHit(Color c) {
        GUIFunctions.changeSelectedColor(c);
        this.selectedColor = c;
    }

    @Override
    public void lineButtonHit() {
        this.selectedShapeController = new LineController();
    }

    @Override
    public void squareButtonHit() {
        this.selectedShapeController = new SquareController();
    }

    @Override
    public void rectangleButtonHit() {
        this.selectedShapeController = new RectangleController();
    }

    @Override
    public void circleButtonHit() {
        this.selectedShapeController = new CircleController();
    }

    @Override
    public void ellipseButtonHit() {
        this.selectedShapeController = new EllipseController();
    }

    @Override
    public void triangleButtonHit() {
        this.selectedShapeController = new TriangleController();
    }

    @Override
    public void selectButtonHit() {

    }

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

    }

    @Override
    public void doMoveBackward() {

    }

    @Override
    public void doSendToFront() {

    }

    @Override
    public void doSendtoBack() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.selectedShapeController.mouseClicked(e, this.model, this.selectedColor);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.selectedShapeController.mousePressed(e, this.model, this.selectedColor);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.selectedShapeController.mouseReleased(e, this.model, this.selectedColor);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.selectedShapeController.mouseEntered(e, this.model, this.selectedColor);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.selectedShapeController.mouseExited(e, this.model, this.selectedColor);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        GUIFunctions.printf("the debugger is here");
        this.selectedShapeController.mouseDragged(e, this.model, this.selectedColor);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.selectedShapeController.mouseMoved(e, this.model, this.selectedColor);
    }
}
