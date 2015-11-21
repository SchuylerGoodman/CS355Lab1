package cs355.model.scene;

import cs355.model.view.Matrix4D;

import java.awt.*;

/**
 * Created by BaronVonBaerenstein on 11/19/2015.
 */
public class StudentInstance implements IInstance {

    private Instance instance;

    public StudentInstance(Instance instance) {
        this.instance = instance;
    }

    @Override
    public Color getColor() {
        return this.instance.getColor();
    }

    @Override
    public void setColor(Color color) {
        this.instance.setColor(color);
    }

    @Override
    public Point3D getPosition() {
        return this.instance.getPosition();
    }

    @Override
    public void setPosition(Point3D pos) {
        this.instance.setPosition(pos);
    }

    @Override
    public double getRotAngle() {
        return this.instance.getRotAngle();
    }

    @Override
    public void setRotAngle(double angle) {
        this.instance.setRotAngle(angle);
    }

    @Override
    public double getScale() {
        return this.instance.getScale();
    }

    @Override
    public void setScale(double scale) {
        this.instance.setScale(scale);
    }

    @Override
    public WireFrame getModel() {
        return this.instance.getModel();
    }

    @Override
    public void setModel(WireFrame model) {
        this.instance.setModel(model);
    }

    @Override
    public Matrix4D getObjectToWorld() {

        // Build rotate matrix
        Matrix4D rotate = new Matrix4D();
        rotate.m00 = Math.cos(this.getRotAngle());
        rotate.m02 = Math.sin(this.getRotAngle());
        rotate.m20 = -1 * Math.sin(this.getRotAngle());
        rotate.m22 = Math.cos(this.getRotAngle());

        // Build translate matrix
        Point3D position = this.getPosition();
        Matrix4D translate = new Matrix4D();
        rotate.m03 = position.x;
        rotate.m13 = position.y;
        rotate.m23 = position.z;

        // Concatenate them
        Matrix4D objToWorld = translate.concatenate(rotate, null);

        return objToWorld;
    }

    @Override
    public Matrix4D getWorldToObject() {
        return null;
    }
}
