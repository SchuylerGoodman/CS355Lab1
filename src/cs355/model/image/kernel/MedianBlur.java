package cs355.model.image.kernel;

import cs355.model.image.CS355Image;
import cs355.model.image.Image;
import cs355.model.view.Vector4D;
import javafx.util.Pair;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by BaronVonBaerenstein on 12/3/2015.
 */
public class MedianBlur extends AlterRGB {

    /**
     * The radius of the kernel (kernel width and height will be (2 * kernelRadius + 1)).
     */
    private int kernelRadius;

    /**
     * Basic constructor for the MedianBlur kernel.
     *
     * @param kernelRadius = the radius of the kernel (kernel width and height will be (2 * kernelRadius + 1)).
     */
    public MedianBlur(int kernelRadius) {
        this.kernelRadius = kernelRadius;
    }

    @Override
    protected int getKernelRadius() {
        return this.kernelRadius;
    }

    @Override
    protected int[] alterPixelRGB(int[] reds, int[] greens, int[] blues) {

        int totalKernelSize = reds.length;

        // Get the median colors and put them in a median color vector for calculating distance
        int[] median = this.getTrueMedianColor(reds, greens, blues);
        Vector4D medianVector = new Vector4D(median[0], median[1], median[2], 0.0);

        // Calculate distance from median for each color and make sortable list of them
        ArrayList<DistanceColorPair> distanceColorPairs = new ArrayList<>();
        for (int i = 0; i < totalKernelSize; ++i) {

            // Make color vector and get distance from median color
            Vector4D colorVector = new Vector4D(reds[i], greens[i], blues[i], 0.0);
            double distance = medianVector.difference(colorVector, null).magnitude();

            // Add pair to list
            int[] color = { reds[i], greens[i], blues[i] };
            DistanceColorPair pair = new DistanceColorPair(distance, color);
            distanceColorPairs.add(pair);
        }

        // Sort pairs by squared distance (increasing)
        Collections.sort(distanceColorPairs);

        // Return the color with lowest distance from median (first in list)
        return distanceColorPairs.get(0).getColor();
    }

    /**
     * Get the median color value for red, green, and blue independently.
     *
     * @param reds = array of red color values.
     * @param greens = array of green color values.
     * @param blues = array of blue color values.
     * @return array with the median red, green and blue.
     */
    private int[] getTrueMedianColor(int[] reds, int[] greens, int[] blues) {

        int copyLength = reds.length;
        int[] redCopy = new int[copyLength];
        int[] greenCopy = new int[copyLength];
        int[] blueCopy = new int[copyLength];

        // Copy and sort the colors
        System.arraycopy(reds, 0, redCopy, 0, copyLength);
        System.arraycopy(greens, 0, greenCopy, 0, copyLength);
        System.arraycopy(blues, 0, blueCopy, 0, copyLength);

        Arrays.sort(redCopy);
        Arrays.sort(greenCopy);
        Arrays.sort(blueCopy);

        // Return the median colors
        int[] median = new int[3];
        int medianIndex = redCopy.length / 2;
        median[0] = redCopy[medianIndex];
        median[1] = greenCopy[medianIndex];
        median[2] = blueCopy[medianIndex];

        return median;
    }

    private class DistanceColorPair extends Pair<Double, int[]> implements Comparable<DistanceColorPair> {
        public DistanceColorPair(Double distance, int[] color) {
            super(distance, color);
        }

        public Double getDistance() {
            return this.getKey();
        }

        public int[] getColor() {
            return this.getValue();
        }

        @Override
        public int compareTo(DistanceColorPair o) {
            return this.getKey().compareTo(o.getKey());
        }
    }

}
