package cs355.model.scene;

import cs355.model.view.Matrix4D;

import java.awt.*;

/**
 * Created by BaronVonBaerenstein on 11/19/2015.
 */
public interface IInstance {

    /**
     * Getter for this Instance's drawing color.
     *
     * @return the color that this Instance should be drawn with.
     */
    public Color getColor();

    /**
     * Setter for this Instance's drawing color.
     *
     * @param color the new color that this
     *              Instance should be drawn with.
     */
    public void setColor(Color color);

    /**
     * Getter for this Instance's position in the 3D world.
     *
     * @return the 3D position of this Instance.
     */
    public Point3D getPosition();

    /**
     * Setter for this Instance's position in the 3D world.
     *
     * @param pos the new 3D position of this Instance.
     */
    public void setPosition(Point3D pos);

    /**
     * Getter for this Instance's rotation angle (y-axis).
     *
     * @return this Instance's rotation angle around the y-axis.
     */
    public double getRotAngle();

    /**
     * Setter for this Instance's rotation angle (y-axis).
     *
     * @param angle the new rotation angle around the y-axis.
     */
    public void setRotAngle(double angle);

    /**
     * Getter for this Instance's scale.
     *
     * @return the scale of this instance.
     */
    public double getScale();

    /**
     * Setter for this Instance's scale.
     *
     * @param scale the new scale for this Instance.
     */
    public void setScale(double scale);

    /**
     * Getter for this Instance's WireFrame model.
     *
     * @return the model of this Instance.
     */
    public WireFrame getModel();

    /**
     * Setter for this Instance's Wireframe model.
     *
     * @param model the new model for this Instance.
     */
    public void setModel(WireFrame model);

    /**
     * Getter for the object to world transform for this instance.
     *
     * @return the object to world transform as a Matrix4D.
     */
    public Matrix4D getObjectToWorld();

    /**
     * Getter for the world to object transform for this instance.
     *
     * @return the world to object transform as a Matrix4D.
     */
    public Matrix4D getWorldToObject();
}