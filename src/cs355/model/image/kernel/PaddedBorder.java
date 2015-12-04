package cs355.model.image.kernel;

import cs355.model.image.CS355Image;

/**
 * Created by BaronVonBaerenstein on 12/3/2015.
 */
public class PaddedBorder implements IBorderPolicy {

    private static final int RGB_COLOR_SIZE = 3;

    /**
     * The color to use as padding around the image.
     */
    private int[] padColor;

    /**
     * The image being padded.
     */
    private CS355Image image;

    /**
     * Basic constructor for a PaddedBorder.
     *
     * @param padColor = the color to use for padding.
     * @param image = the image to pad.
     */
    public PaddedBorder(int[] padColor, CS355Image image) {
        this.padColor = padColor;
        this.image = image;
    }

    @Override
    public int[] getPixel(int x, int y, int[] data) {

        if (data == null || data.length < 3) {
            data = new int[RGB_COLOR_SIZE];
        }

        if (this.coordinatesInBounds(x, y)) {
            image.getPixel(x, y, data);
        }
        else {
            System.arraycopy(this.padColor, 0, data, 0, RGB_COLOR_SIZE);
        }

        return data;
    }

    @Override
    public int getRed(int x, int y) {

        if (this.coordinatesInBounds(x, y)) {
            return image.getRed(x, y);
        }
        return this.padColor[0];
    }

    @Override
    public int getGreen(int x, int y) {

        if (this.coordinatesInBounds(x, y)) {
            return image.getGreen(x, y);
        }
        return this.padColor[1];
    }

    @Override
    public int getBlue(int x, int y) {

        if (this.coordinatesInBounds(x, y)) {
            return image.getBlue(x, y);
        }
        return this.padColor[2];
    }

    /**
     * Checks if the given coordinates are within the bounds of the image.
     *
     * @param x = x-coordinate to check.
     * @param y = y-coordinate to check.
     * @return true if within bounds, otherwise false.
     */
    private boolean coordinatesInBounds(int x, int y) {

        if ( (x >= 0 && x < image.getWidth()) && (y >= 0 && y < image.getHeight()) ) {
            return true;
        }
        return false;
    }
}
