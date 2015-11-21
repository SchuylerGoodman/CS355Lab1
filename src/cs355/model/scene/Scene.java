package cs355.model.scene;

import cs355.model.camera.Camera;
import cs355.model.camera.ICamera;
import cs355.model.view.Vector4D;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by BaronVonBaerenstein on 11/19/2015.
 */
public class Scene extends Observable implements IScene {

    private CS355Scene internalScene;
    private ICamera camera;

    private ArrayList<IInstance> instances;

    public Scene() {

        // Save CS355Scene internally
        internalScene = new CS355Scene();

        // Convert pre-existing instances to good instance type
        this.instances = this.convertInstances(this.internalScene.instances());

        // Initialize the camera to the scene defaults
        this.resetCamera();
    }

    @Override
    public boolean open(File f) {

        // Open scene file using internal scene
        boolean opened = this.internalScene.open(f);

        // Since instances are new, convert to good instance type
        this.instances = this.convertInstances(this.internalScene.instances());

        this.setChanged();
        return opened;
    }

    @Override
    public ArrayList<IInstance> instances() {
        return this.instances;
    }

    @Override
    public Point4D getCameraPosition() {
        return this.camera.getLocation();
    }

    @Override
    public void setCameraPosition(Point4D pos) {

        this.camera.setLocation(pos);

        this.setChanged();
    }

    @Override
    public double getCameraRotation() {
        return this.camera.getRotationAngle();
    }

    @Override
    public void setCameraRotation(double rot) {

        Vector4D newForwardAxis = new Vector4D(
                Math.sin(rot),
                0.0,
                Math.cos(rot),
                0.0
        );

        this.camera.setForwardAxis(newForwardAxis);
        this.setChanged();
    }

    @Override
    public Vector4D getCameraForward() {
        return this.camera.getForwardAxis();
    }

    @Override
    public Vector4D getCameraRight() {
        return this.camera.getRightAxis();
    }

    @Override
    public Vector4D getCameraUp() {
        return this.camera.getUpAxis();
    }

    @Override
    public void resetCamera() {
        Point4D position = new Point4D(this.internalScene.getCameraPosition());
        this.camera = new Camera(
                position,
                this.internalScene.getCameraRotation()
        );

        this.setChanged();
    }

    private ArrayList<IInstance> convertInstances(List<Instance> instances) {

        ArrayList<IInstance> newInstances = new ArrayList<>();
        for (Instance instance : instances) {
            newInstances.add(new StudentInstance(instance));
        }

        return newInstances;
    }
}
