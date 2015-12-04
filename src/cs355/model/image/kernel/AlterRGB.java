package cs355.model.image.kernel;

import cs355.model.image.CS355Image;

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

        // For every pixel in the image
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {

                int[] reds = new int[totalKernelSize];
                int[] greens = new int[totalKernelSize];
                int[] blues = new int[totalKernelSize];

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
                int[] color = this.alterPixelRGB(reds, greens, blues);

                // Set the pixel to the color of the average
                image.setPixel(x, y, color);
            }
        }
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
     * @return a 3+ element int[] with the RGB values resulting from the alteration.
     *                      For kernelRadius = 1, an array like {0, 1, 2, 3, 4, 5, 6, 7, 8} will contain
     *                          the color values in the orientation:
     *                          0 3 6
     *                          1 4 7
     *                          2 5 8
     *                      This is done for efficiency.
     */
    protected abstract int[] alterPixelRGB(int[] reds, int[] greens, int[] blues);

}
