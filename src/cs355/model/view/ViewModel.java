package cs355.model.view;

import cs355.GUIFunctions;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/17/2015.
 */
public class ViewModel extends AbstractViewModel {

    private static double NEUTRAL_WIDTH = 512;

    private Point2D.Double center;

    private ZoomLevel zoomLevel;

    private int scrollMin;

    private int scrollMax;

    private int knobSize;

    private int horizontalScrollPosition;

    private int verticalScrollPosition;

    public ViewModel() {

        this.center = new Point2D.Double(NEUTRAL_WIDTH / 2.0, NEUTRAL_WIDTH / 2.0);
        this.zoomLevel = ZoomLevel.NEUTRAL;

        // scroll min is 0
        this.scrollMin = 0;

        // scroll bar max is the neutral (starting) width / most zoomed out zoom factor
        this.scrollMax = (int) ( NEUTRAL_WIDTH / ZoomLevel.NONE.getFactor() );

        // start scroll bars at origin
        this.horizontalScrollPosition = 0;
        this.verticalScrollPosition = 0;
    }

    public void setCenter(Point2D.Double center) {
        this.center.setLocation(center);
    }

    public void setHScrollPosition(int horizontalScrollPosition) {
        this.horizontalScrollPosition = horizontalScrollPosition;
    }

    public void setVScrollPosition(int verticalScrollPosition) {
        this.verticalScrollPosition = verticalScrollPosition;
    }

    public void zoomIn() {

        // set zoom level to one level in
        this.zoomLevel = this.zoomLevel.in();

        // update scroll knobs
        int knobSize = (int) ( NEUTRAL_WIDTH / this.zoomLevel.getFactor() );
        GUIFunctions.setHScrollBarKnob(knobSize);
        GUIFunctions.setVScrollBarKnob(knobSize);

        // update view
        this.setChanged();
    }

    public void zoomOut() {

        // set zoom level to one level out
        this.zoomLevel = this.zoomLevel.out();

        // update scroll knobs
        int knobSize = (int) ( NEUTRAL_WIDTH / this.zoomLevel.getFactor() );
        GUIFunctions.setHScrollBarKnob(knobSize);
        GUIFunctions.setVScrollBarKnob(knobSize);

        // update view
        this.setChanged();
    }

    public double getZoomFactor() {
        return this.zoomLevel.getFactor();
    }

    public AffineTransform getViewToWorld() {
        // TODO test transform methods
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

    /**
     * Calculates the translation vector necessary to keep the view at this center and zoom level.
     *
     * @return a Point2D.Double representing how much the view should be translated.
     */
    private Point2D.Double getTranslation() {
        Point2D.Double translation = new Point2D.Double();

        double zoomDifference = NEUTRAL_WIDTH / ( 2 * this.zoomLevel.getFactor() );
        translation.setLocation(
                this.center.getX() - zoomDifference,
                this.center.getY() - zoomDifference
        );

        return translation;
    }

    /**
     * Updates the view.
     * Called when the zoom level or the scroll position changes.
     */
    private void updateView() {

        // notify observers
        this.setChanged();
        this.notifyObservers();
    }

    private enum ZoomLevel {
        NONE(0.25), PARTIAL(0.5), NEUTRAL(1), MOST(2), FULL(4);

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
