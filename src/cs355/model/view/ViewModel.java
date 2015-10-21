package cs355.model.view;

import cs355.GUIFunctions;

import javax.swing.text.View;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Created by BaronVonBaerenstein on 10/17/2015.
 */
public class ViewModel extends AbstractViewModel {

    private static double NEUTRAL_WIDTH = 512;

    private static int SCROLL_MIN = 0;

    // scroll bar max is the neutral (starting) width / most zoomed out zoom factor
    // minus one so the total range still has 4 as a factor.
    private static int SCROLL_MAX = (int) ( NEUTRAL_WIDTH / ZoomLevel.NONE.getFactor() ) - 1;

    private Point2D.Double center;

    private ZoomLevel zoomLevel;

    private int scrollMin;

    private int scrollMax;

    private int knobSize;

    public ViewModel() {

        // start at neutral zoom with top-left corner at origin
        this.center = new Point2D.Double(NEUTRAL_WIDTH / 2.0, NEUTRAL_WIDTH / 2.0);
        this.zoomLevel = ZoomLevel.NEUTRAL;
    }

    @Override
    public void updateFrame() {

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

    }

    public void setCenter(Point2D.Double newCenter) {
        this.center.setLocation(newCenter);

        this.setChanged();
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

        this.setChanged();
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

        this.setChanged();
    }

    public void zoomIn() {

        // set zoom level to one level in
        this.zoomLevel = this.zoomLevel.in();

        // validate center position
        //this.setHScrollPosition(this.getHScrollBarPosit());
        //this.setVScrollPosition(this.getVScrollBarPosit());

        // update view
        this.setChanged();
    }

    public void zoomOut() {

        // set zoom level to one level out
        this.zoomLevel = this.zoomLevel.out();

        // validate center position
        this.setHScrollPosition(this.getHScrollBarPosit());
        this.setVScrollPosition(this.getVScrollBarPosit());

        // update view
        this.setChanged();
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
        //int position = this.getScrollBarPosit(this.center.getX());
        //this.setHScrollPosition(position);

        //return position;
    }

    /**
     * Gets the vertical scroll bar position.
     *
     * @return the position of the top of the view in world space.
     */
    private int getVScrollBarPosit() {
        return (int) ( this.center.getY() - ( this.getKnobSize() / 2 ) );
        //int position = this.getScrollBarPosit(this.center.getY());
        //this.setVScrollPosition(position);

        //return position;
    }

    private int getScrollBarPosit(double centerCoord) {
        int position = (int) ( centerCoord - ( this.getKnobSize() / 2 ) );
        if (position < ViewModel.SCROLL_MIN) {
            position = ViewModel.SCROLL_MIN;
        }
        else if (position > ViewModel.SCROLL_MAX - this.getKnobSize() + 1) {
            position = ViewModel.SCROLL_MAX - this.getKnobSize();
        }

        return position;
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
