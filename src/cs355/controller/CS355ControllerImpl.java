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
    }

    @Override
    public void lineButtonHit() {
        this.selectedController = new LineController();
    }

    @Override
    public void squareButtonHit() {
        this.selectedController = new SquareController();
    }

    @Override
    public void rectangleButtonHit() {
        this.selectedController = new RectangleController();
    }

    @Override
    public void circleButtonHit() {
        this.selectedController = new CircleController();
    }

    @Override
    public void ellipseButtonHit() {
        this.selectedController = new EllipseController();
    }

    @Override
    public void triangleButtonHit() {
        this.selectedController = new TriangleController();
    }

    @Override
    public void selectButtonHit() { this.selectedController = new SelectController(); }

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
}
