package cs355.model.view;

import cs355.GUIFunctions;
import cs355.model.scene.IScene;
import cs355.model.scene.Point4D;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Observable;

/**
 * Created by BaronVonBaerenstein on 10/17/2015.
 */
public class ViewModel extends Observable implements IViewModel {

    private static double NEUTRAL_WIDTH = 512;

    private static int SCROLL_MIN = 0;

    // scroll bar max is the neutral (starting) width / most zoomed out zoom factor
    // minus one so the total range still has 4 as a factor.
    private static int SCROLL_MAX = (int) ( NEUTRAL_WIDTH / ZoomLevel.NONE.getFactor() ) - 1;

    // For perspective projection
    private static final double ASPECT_RATIO = 1.0;
    private static final double MAX_FIELD_OF_VIEW_Y = Math.toRadians(75.0);
    private static final double MAX_FIELD_OF_VIEW_X = ASPECT_RATIO * MAX_FIELD_OF_VIEW_Y;

    // For clipping
    private static final double CLIP_NEAR = 1.0;
    private static final double CLIP_FAR = 200.0;

    /**
     * The scene for the 3D display
     */
    private IScene scene;

    private Point2D.Double center;

    private ZoomLevel zoomLevel;

    private boolean canUpdate;

    private boolean display3DIsOn;

    public ViewModel(IScene scene) {

        this.scene = scene;

        // start at neutral zoom with top-left corner at origin
        this.center = new Point2D.Double(NEUTRAL_WIDTH / 2.0, NEUTRAL_WIDTH / 2.0);
        this.zoomLevel = ZoomLevel.NONE;
        this.canUpdate = true;
        this.display3DIsOn = false;
    }

    @Override
    public void updateFrame() {

        this.canUpdate = false;

        int knobSize = this.getKnobSize();

        GUIFunctions.setHScrollBarMin(ViewModel.SCROLL_MIN);
        GUIFunctions.setHScrollBarMax(ViewModel.SCROLL_MAX);
        int hPosit = this.getHScrollBarPosit();
        GUIFunctions.setHScrollBarKnob(knobSize);
        GUIFunctions.setHScrollBarPosit(hPosit);
        GUIFunctions.setHScrollBarKnob(knobSize);

        GUIFunctions.setVScrollBarMin(ViewModel.SCROLL_MIN);
        GUIFunctions.setVScrollBarMax(ViewModel.SCROLL_MAX);
        int vPosit = this.getVScrollBarPosit();
        GUIFunctions.setVScrollBarKnob(knobSize);
        GUIFunctions.setVScrollBarPosit(vPosit);
        GUIFunctions.setVScrollBarKnob(knobSize);

        GUIFunctions.setZoomText(this.getZoomFactor());

        this.setChanged();

        this.canUpdate = true;
    }

    public void setCenter(Point2D.Double newCenter) {
        this.center.setLocation(newCenter);

        if (this.canUpdate) {
            this.setChanged();
        }
    }

    public void setHScrollPosition(int horizontalScrollPosition) {

        int knobSize = this.getKnobSize();

        if (horizontalScrollPosition + knobSize > ViewModel.SCROLL_MAX) {
            horizontalScrollPosition = ViewModel.SCROLL_MAX - knobSize;
        }

        if (horizontalScrollPosition <= ViewModel.SCROLL_MIN) {
            horizontalScrollPosition = ViewModel.SCROLL_MIN;
        }

        this.setCenter(new Point2D.Double(
                horizontalScrollPosition + (knobSize / 2),
                this.center.getY()
        ));

        if (this.canUpdate) {
            this.setChanged();
        }
    }

    public void setVScrollPosition(int verticalScrollPosition) {

        int knobSize = this.getKnobSize();

        if (verticalScrollPosition + knobSize > ViewModel.SCROLL_MAX) {
            verticalScrollPosition = ViewModel.SCROLL_MAX - knobSize;
        }

        if (verticalScrollPosition <= ViewModel.SCROLL_MIN) {
            verticalScrollPosition = ViewModel.SCROLL_MIN;
        }

        this.setCenter(new Point2D.Double(
                this.center.getX(),
                verticalScrollPosition + (knobSize / 2)
        ));

        if (this.canUpdate) {
            this.setChanged();
        }
    }

    public void zoomIn() {

        // set zoom level to one level in
        this.zoomLevel = this.zoomLevel.in();

        // update view
        if (this.canUpdate) {
            this.setChanged();
        }
    }

    public void zoomOut() {

        // set zoom level to one level out
        this.zoomLevel = this.zoomLevel.out();

        // validate center position
        this.setHScrollPosition(this.getHScrollBarPosit());
        this.setVScrollPosition(this.getVScrollBarPosit());

        // update view
        if (this.canUpdate) {
            this.setChanged();
        }
    }

    public double getZoomFactor() {
        return this.zoomLevel.getFactor();
    }

    public AffineTransform getViewToWorld() {

        // get translate
        Point2D.Double translation = this.getTranslation();

        // get translate matrix
        AffineTransform viewToWorld = new AffineTransform(1, 0, 0, 1, translation.getX(), translation.getY());

        // get scaling (1/Z) matrix
        double scale = 1 / this.zoomLevel.getFactor();
        AffineTransform scalingTransform = new AffineTransform(scale, 0, 0, scale, 0, 0);

        // concatenate them
        viewToWorld.concatenate(scalingTransform);

        return viewToWorld;
    }

    public AffineTransform getWorldToView() {

        // get scaling matrix (Z)
        double scale = this.zoomLevel.getFactor();
        AffineTransform worldToView = new AffineTransform(scale, 0, 0, scale, 0, 0);

        // get translate
        Point2D.Double translation = this.getTranslation();
        translation.setLocation(
                translation.getX() * -1,
                translation.getY() * -1
        );

        // get translate matrix
        AffineTransform translationTransform = new AffineTransform(1, 0, 0, 1, translation.getX(), translation.getY());

        // concatenate them
        worldToView.concatenate(translationTransform);

        return worldToView;
    }

    @Override
    public Matrix4D getWorldToClip() {

        // get camera coordinates
        Point4D cameraPosition = this.scene.getCameraPosition();

        // negate camera coordinates
        double camX = -1 * cameraPosition.x;
        double camY = -1 * cameraPosition.y;
        double camZ = -1 * cameraPosition.z;

        // initialize translation matrix
        Matrix4D translate = new Matrix4D();
        translate.m03 = camX;
        translate.m13 = camY;
        translate.m23 = camZ;

        // get camera axes
        Vector4D forwardAxis = this.scene.getCameraForward();
        Vector4D rightAxis = this.scene.getCameraRight();
        Vector4D upAxis = this.scene.getCameraUp();

        // initialize rotation matrix
        Matrix4D rotate = new Matrix4D();
        rotate.m00 = rightAxis.v0;
        rotate.m01 = rightAxis.v1;
        rotate.m02 = rightAxis.v2;
        rotate.m10 = upAxis.v0;
        rotate.m11 = upAxis.v1;
        rotate.m12 = upAxis.v2;
        rotate.m20 = forwardAxis.v0;
        rotate.m21 = forwardAxis.v1;
        rotate.m22 = forwardAxis.v2;

        // get zoom_y
        double zoomY = 1 / ( Math.tan(MAX_FIELD_OF_VIEW_Y / 2.0) );

        // get zoom_x
        double zoomX = 1 / ( Math.tan(MAX_FIELD_OF_VIEW_X / 2.0) );

        // initialize clip matrix
        Matrix4D clip = new Matrix4D();
        clip.m00 = zoomX;
        clip.m11 = zoomY;
        clip.m22 = (CLIP_FAR + CLIP_NEAR) / (CLIP_FAR - CLIP_NEAR);
        clip.m23 = (-2 * CLIP_NEAR * CLIP_FAR) / (CLIP_FAR - CLIP_NEAR);
        clip.m32 = 1.0;
        clip.m33 = 0.0;

        // concatenate matrices
        Matrix4D worldToClip = clip.concatenate(rotate.concatenate(translate, null), null);

        return worldToClip;
    }

    @Override
    public Matrix4D getClipToWorld() {
        return null;
    }

    @Override
    public Matrix3D getCanonicalToScreen() {

        // get width of screen
        double width = NEUTRAL_WIDTH;

        // get height of screen
        double height = NEUTRAL_WIDTH / ASPECT_RATIO;

        // initialize screen matrix
        Matrix3D canonicalToScreen = new Matrix3D();
        canonicalToScreen.m00 = width / ( this.getZoomFactor() * 2.0 );
        canonicalToScreen.m02 = width / 2.0;
        canonicalToScreen.m11 = -1 * height / ( this.getZoomFactor() * 2.0 );
        canonicalToScreen.m12 = height / 2.0;

        return canonicalToScreen;
    }

    @Override
    public Matrix3D getScreenToCanonical() {
        return null;
    }

    @Override
    public void toggle3DModelDisplay() {

        // toggle model displayed boolean
        this.display3DIsOn = !this.display3DIsOn;

        this.setChanged();

    }

    @Override
    public boolean is3DModelDisplayed() {
        return this.display3DIsOn;
    }

    /**
     * Gets the size of the scroll knobs.
     *
     * @return the scroll knob size.
     */
    private int getKnobSize() {
        return (int) ( NEUTRAL_WIDTH / this.zoomLevel.getFactor() );
    }

    /**
     * Gets the horizontal scroll bar position.
     *
     * @return the position of the left side of the view in world space.
     */
    private int getHScrollBarPosit() {
        return (int) ( this.center.getX() - ( this.getKnobSize() / 2 ) );
    }

    /**
     * Gets the vertical scroll bar position.
     *
     * @return the position of the top of the view in world space.
     */
    private int getVScrollBarPosit() {
        return (int) ( this.center.getY() - ( this.getKnobSize() / 2 ) );
    }

    /**
     * Calculates the translation vector necessary to keep the view at this center and zoom level.
     *
     * @return a Point2D.Double representing how much the view should be translated.
     */
    private Point2D.Double getTranslation() {
        Point2D.Double translation = new Point2D.Double();

        translation.setLocation(this.getHScrollBarPosit(), this.getVScrollBarPosit());
        return translation;
    }

    private enum ZoomLevel {
        NONE(0.25), PARTIAL(0.5), NEUTRAL(1.0), MOST(2.0), FULL(4.0);

        private final double factor;
        private ZoomLevel(double factor) {
            this.factor = factor;
        }

        /**
         * Get the next zoom level in the enum.
         *
         * @return one zoom level further in, or the same if at full zoom.
         */
        public ZoomLevel in() {
            return this.ordinal() < ZoomLevel.values().length - 1
                    ? ZoomLevel.values()[this.ordinal() + 1]
                    : this;
        }

        /**
         * Get the previous zoom level in the enum.
         *
         * @return one zoom level further out, or the same if at no zoom.
         */
        public ZoomLevel out() {
            return this.ordinal() > 0
                    ? ZoomLevel.values()[this.ordinal() - 1]
                    : this;
        }

        /**
         * Getter for the multiplicative factor for the zoom level
         *
         * @return the factor this zoom level represents.
         */
        public double getFactor() {
            return this.factor;
        }
    }
}
