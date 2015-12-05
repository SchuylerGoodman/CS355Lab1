package cs355.model.image.kernel;

import com.sun.javaws.exceptions.InvalidArgumentException;
import cs355.model.image.CS355Image;
import cs355.model.view.Vector4D;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by BaronVonBaerenstein on 12/3/2015.
 */
public abstract class AlterRGB implements IKernel {

    @Override
    public void apply(CS355Image image, IBorderPolicy borderPolicy) {
        this.alterRGB(image, borderPolicy, this.getKernelRadius());
    }

    /**
     * Alters the RGB values for an image according to some function.
     *
     * @param image = the image to alter.
     * @param borderPolicy = the border policy for querying values outside the bounds of the image.
     * @param kernelRadius = the radius of the spacial filtering kernel.
     */
    protected void alterRGB(CS355Image image, IBorderPolicy borderPolicy, int kernelRadius) {

        int totalKernelSize = (int) Math.pow(2 * kernelRadius + 1, 2);

        int width = image.getWidth();
        int height = image.getHeight();

        int[] reds = new int[totalKernelSize];
        int[] greens = new int[totalKernelSize];
        int[] blues = new int[totalKernelSize];
        int[] color = new int[3];

        // For every pixel in the image
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {


                // Grab the colors of all the surrounding pixels
                int p = 0;
                for (int kx = x - kernelRadius; kx <= x + kernelRadius; ++kx) {
                    for (int ky = y - kernelRadius; ky <= y + kernelRadius; ++ky) {
                        reds[p] = borderPolicy.getRed(kx, ky);
                        greens[p] = borderPolicy.getGreen(kx, ky);
                        blues[p] = borderPolicy.getBlue(kx, ky);
                        ++p;
                    }
                }

                // Get the result of the alteration
                try {
                    this.alterPixelRGB(reds, greens, blues, color);
                }
                catch (Exception e) {
                    borderPolicy.getPixel(x, y, color);
                }

                // Set the pixel to the color of the average
                image.setPixel(x, y, color);
            }
        }
    }

    /**
     * Get the color closest to the target color in the color arrays.
     *
     * @param target = the target color to compare to.
     * @param reds = the red color values to pick from.
     * @param greens = the green color values to pick from.
     * @param blues = the blue color values to pick from.
     * @param data = the 3+ element int[] to store the return value in. If it is null or has length < 3 it will
     *             be initialized and returned.
     * @return a 3+ element int[] to store the closest color in.
     */
    protected int[] getClosestColor(int[] target, int[] reds, int[] greens, int[] blues, int[] data) {

        if (data == null || data.length < 3) {
            data = new int[3];
        }

        int totalKernelSize = reds.length;

        // Calculate distance from target for each color and make sortable list of them
        double startingDistance = -1;
        double bestDistance = startingDistance;
        for (int i = 0; i < totalKernelSize; ++i) {
            double distance = this.getDistanceSquared(target[0], target[1], target[2], reds[i], greens[i], blues[i]);
            if (bestDistance > distance || bestDistance == startingDistance) {
                data[0] = reds[i];
                data[1] = greens[i];
                data[2] = blues[i];
                bestDistance = distance;
            }
        }

        return data;
    }

    /**
     * Gets the radius of the kernel.
     *
     * @return the radius of the kernel as an int.
     */
    protected abstract int getKernelRadius();

    /**
     * Alters the RGB values of a pixel according to a kernel.
     *
     * @param reds = an int[] of red values, the length is (2 * kernelRadius + 1)^2.
     * @param greens = an int[] of green values, the length is (2 * kernelRadius + 1)^2.
     * @param blues = an int[] of blue values, the length is (2 * kernelRadius + 1)^2.
     * @param data = the 3+ element int[] to store the return value in. If it is null or has length < 3 it will
     *             be initialized and returned.
     * @return a 3+ element int[] with the RGB values resulting from the alteration.
     *                      For kernelRadius = 1, an array like {0, 1, 2, 3, 4, 5, 6, 7, 8} will contain
     *                          the color values in the orientation:
     *                          0 3 6
     *                          1 4 7
     *                          2 5 8
     *                      This is done for efficiency.
     */
    protected abstract int[] alterPixelRGB(int[] reds, int[] greens, int[] blues, int[] data) throws InvalidArgumentException;

    /**
     * Get the distance squared between two colors as if they were vectors.
     *
     * @param targetRed = the target red value.
     * @param targetGreen = the target green value.
     * @param targetBlue = the target blue value.
     * @param red = the pixel red value.
     * @param green = the pixel green value.
     * @param blue = the pixel blue value.
     * @return the distance^2 between the two colors as if they were vectors.
     */
    private double getDistanceSquared(int targetRed, int targetGreen, int targetBlue, int red, int green, int blue) {
        int dRed = targetRed - red;
        int dGreen = targetGreen - green;
        int dBlue = targetBlue - blue;

        double distanceSquared = Math.pow(dRed, 2) + Math.pow(dGreen, 2) + Math.pow(dBlue, 2);

        return distanceSquared;
    }

    /*
    private class DistanceColorPair implements Comparable<DistanceColorPair> {

        private Double distance;

        private int red;

        private int green;

        private int blue;

        public DistanceColorPair(Double distance, int red, int green, int blue) {
            this.distance = distance;
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public Double getDistance() {
            return this.distance;
        }

        public int getRed() {
            return this.red;
        }

        public int getGreen() {
            return this.green;
        }

        public int getBlue() {
            return this.blue;
        }

        @Override
        public int compareTo(DistanceColorPair o) {
            return this.getDistance().compareTo(o.getDistance());
        }
    }
    */
}
