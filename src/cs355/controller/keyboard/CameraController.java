package cs355.controller.keyboard;

import cs355.model.scene.IScene;
import cs355.model.scene.Point3D;
import cs355.model.camera.ICamera;
import cs355.model.scene.Point4D;
import cs355.model.view.Matrix3D;
import cs355.model.view.Matrix4D;
import cs355.model.view.Vector4D;

import java.awt.event.KeyEvent;
import java.util.Iterator;

/**
 * Created by BaronVonBaerenstein on 11/17/2015.
 */
public class CameraController implements IKeyboardEventController {

    private static final int MOVE_AMOUNT = 1;
    private static final int ROTATE_AMOUNT = 1;

    private IScene scene;

    public CameraController(IScene scene) {
        this.scene = scene;
    }

    @Override
    public void keyPressed(Iterator<Integer> iterator) {

        while (iterator.hasNext()) {

            int code = iterator.next();

            if (KeyEvent.VK_A == code) {
                this.moveLeft(MOVE_AMOUNT);
            }
            else if (KeyEvent.VK_D == code) {
                this.moveRight(MOVE_AMOUNT);
            }
            else if (KeyEvent.VK_W == code) {
                this.moveForward(MOVE_AMOUNT);
            }
            else if (KeyEvent.VK_S == code) {
                this.moveBackward(MOVE_AMOUNT);
            }
            else if (KeyEvent.VK_R == code) {
                this.moveUp(MOVE_AMOUNT);
            }
            else if (KeyEvent.VK_F == code) {
                this.moveDown(MOVE_AMOUNT);
            }
            else if (KeyEvent.VK_Q == code) {
                this.turnLeft(ROTATE_AMOUNT);
            }
            else if (KeyEvent.VK_E == code) {
                this.turnRight(ROTATE_AMOUNT);
            }
            else if (KeyEvent.VK_H == code) {
                this.reset();
            }
        }
    }

    /**
     * Moves the camera to the left by amount.
     *
     * @param amount = the amount to move the camera.
     */
    private void moveLeft(int amount) {

        // Get the right axis vector (assumed to be normalized)
        Vector4D rightAxis = this.scene.getCameraRight();

        // Reverse the right axis
        Vector4D leftAxis = new Vector4D(
                rightAxis.v0 * -1,
                rightAxis.v1 * -1,
                rightAxis.v2 * -1,
                0.0
        );

        // Move "amount" in the direction of the left axis
        this.moveInDirection(leftAxis, amount);
    }

    /**
     * Moves the camera to the right by amount.
     *
     * @param amount = the amount to move the camera.
     */
    private void moveRight(int amount) {

        // Get the right axis vector (assumed to be normalized)
        Vector4D rightAxis = this.scene.getCameraRight();

        // Move "amount" in the direction of the right axis
        this.moveInDirection(rightAxis, amount);
    }

    /**
     * Moves the camera forward by amount.
     *
     * @param amount = the amount to move the camera.
     */
    private void moveForward(int amount) {

        // Get the forward axis vector (assumed to be normalized)
        Vector4D forwardAxis = this.scene.getCameraForward();

        // Move "amount" in the direction of the forward axis
        this.moveInDirection(forwardAxis, amount);
    }

    /**
     * Moves the camera backward by amount.
     *
     * @param amount = the amount to move the camera.
     */
    private void moveBackward(int amount) {

        // Get the forward axis vector (assumed to be normalized)
        Vector4D forwardAxis = this.scene.getCameraForward();

        // Reverse the forward axis
        Vector4D backwardAxis = new Vector4D(
                forwardAxis.v0 * -1,
                forwardAxis.v1 * -1,
                forwardAxis.v2 * -1,
                0.0
        );

        // Move "amount" in the direction of the backward axis
        this.moveInDirection(backwardAxis, amount);
    }

    /**
     * Moves the camera up by amount.
     *
     * @param amount = the amount to move the camera.
     */
    private void moveUp(int amount) {

        // Get the up axis vector (assumed to be normalized)
        Vector4D upAxis = this.scene.getCameraUp();

        // Move "amount" in the direction of the up axis
        this.moveInDirection(upAxis, amount);
    }

    /**
     * Moves the camera down by amount.
     *
     * @param amount = the amount to move the camera.
     */
    private void moveDown(int amount) {

        // Get the up axis vector (assumed to be normalized)
        Vector4D upAxis = this.scene.getCameraUp();

        // Reverse the up axis
        Vector4D downAxis = new Vector4D(
                upAxis.v0 * -1,
                upAxis.v1 * -1,
                upAxis.v2 * -1,
                0.0
        );

        // Move "amount" in the direction of the down axis
        this.moveInDirection(downAxis, amount);
    }

    private void moveInDirection(Vector4D direction, int amount) {

        // Get the current camera location
        Point4D location = this.scene.getCameraPosition();

        // Move in the direction of the right axis
        Point4D newLocation = new Point4D(
                location.x + direction.v0 * amount,
                location.y + direction.v1 * amount,
                location.z + direction.v2 * amount,
                1.0
        );

        // Update camera location
        this.scene.setCameraPosition(newLocation);
    }

    /**
     * Turns the camera to the left by amount.
     *
     * @param degrees = the number of degrees to turn the camera.
     */
    private void turnLeft(int degrees) {

        // Get the angle in radians
        double angle = Math.toRadians(degrees);

        // Get the current rotation and add degrees
        double cameraRotation = this.scene.getCameraRotation();
        double newRotation = cameraRotation + degrees;

        // Set rotation to new rotation
        this.scene.setCameraRotation(newRotation);
    }

    /**
     * Turns the camera to the right by amount.
     *
     * @param degrees = the number of degrees to turn the camera.
     */
    private void turnRight(int degrees) {

        // Negate degrees
        int negDegrees = -1 * degrees;

        // Turn left with negative degrees
        this.turnLeft(negDegrees);
    }

    /**
     * Reset the camera defaults.
     */
    private void reset() {
        this.scene.resetCamera();
    }
}
