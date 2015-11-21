package cs355.model.scene;

import cs355.model.IObservable;
import cs355.model.view.Vector4D;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by BaronVonBaerenstein on 11/19/2015.
 */
public interface IScene extends IObservable {

    /**
     * Open a file and create a scene from it.
     * This uses SceneParser to parse the file.
     * @param f the file to open.
     * @return true if successful, false otherwise.
     */
    public boolean open(File f);

    /**
     * Gets the list of Instances in the scene.
     * @return the list of Instances in the scene.
     */
    public ArrayList<IInstance> instances();

    /**
     * Get the default camera position.
     * @return the position of the default camera.
     */
    public Point4D getCameraPosition();

    /**
     * Sets the camera default position.
     * @param pos the default position for the camera.
     */
    public void setCameraPosition(Point4D pos);

    /**
     * Get the rotation angle of the default camera.
     * @return the rotation angle of the default camera.
     */
    public double getCameraRotation();

    /**
     * Sets the camera's rotation.
     * @param rot the new rotation for the camera.
     *			  This is normalized.
     */
    public void setCameraRotation(double rot);

    /**
     * Get the camera axis that points forward in world coordinates.
     *
     * @return the forward-facing camera axis as a Vector4D.
     */
    public Vector4D getCameraForward();

    /**
     * Get the camera axis that points to the right in world coordinates.
     *
     * @return the right-facing camera axis as a Vector4D.
     */
    public Vector4D getCameraRight();

    /**
     * Get the camera axis that points up in world coordinates.
     *
     * @return the up-facing camera axis as a Vector4D.
     */
    public Vector4D getCameraUp();

    /**
     * Reset the camera to the scene defaults.
     */
    public void resetCamera();

}