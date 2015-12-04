package cs355.model.image;

import cs355.model.image.kernel.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;

/**
 * Created by BaronVonBaerenstein on 12/1/2015.
 */
public class Image extends CS355Image {

    // Image adjustment constants
    private static final int BRIGHTNESS_THRESHOLD = 100;
    private static final int CONTRAST_THRESHOLD = 100;

    // Spatial filtering constants
    private static final int KERNEL_RADIUS = 1;
    private static final int[] BORDER_COLOR = {0, 0, 0};

    public Image() {
        super();
    }

    @Override
    public BufferedImage getImage() {

        int width = this.getWidth();
        int height = this.getHeight();

        // If width or height invalid, quit because image isn't loaded yet
        if (width <= 0 || height <= 0) {
            return null;
        }

        // Create RGB buffered image
        BufferedImage image = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB
        );

        // Set the rgb value for every pixel in the image
        int[] rgb = new int[3];
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                this.getPixel(x, y, rgb);
                int rgbValue = new Color(rgb[0], rgb[1], rgb[2]).getRGB();
                image.setRGB(x, y, rgbValue);
            }
        }

        return image;
    }

    @Override
    public void edgeDetection() {

        this.setImageChanged();
    }

    @Override
    public void sharpen() {

        // Initialize and apply sharpen kernel
        int A = 2;
        IKernel sharpen = new Sharpen(A);
        this.applyKernel(sharpen);

        this.setImageChanged();
    }

    @Override
    public void medianBlur() {

        // Initialize and apply median blur filter
        IKernel medianFilter = new MedianBlur(KERNEL_RADIUS);
        this.applyKernel(medianFilter);

        this.setImageChanged();
    }

    @Override
    public void uniformBlur() {

        // Initialize and apply uniform blur
        IKernel uniformBlur = new UniformBlur(KERNEL_RADIUS);
        this.applyKernel(uniformBlur);

        this.setImageChanged();
    }

    @Override
    public void grayscale() {

        Function<float[], float[]> makeGrayscale = hsb -> {
            // Zero the saturation
            hsb[1] = 0.0f;

            return hsb;
        };

        // Apply the alteration
        this.alterImageHSB(makeGrayscale);

        this.setImageChanged();
    }

    @Override
    public void contrast(int amount) {

        // Threshold brightness
        if (amount < -1 * CONTRAST_THRESHOLD) {
            amount = -1 * CONTRAST_THRESHOLD;
        }
        else if (amount > CONTRAST_THRESHOLD) {
            amount = CONTRAST_THRESHOLD;
        }

        final float changeAmount = amount;

        Function<float[], float[]> changeContrast = hsb -> {
            // Alter contrast
            float ratio = (float) Math.pow(( changeAmount + CONTRAST_THRESHOLD ) / (float) CONTRAST_THRESHOLD, 4);
            hsb[2] = ratio * ( hsb[2] - 0.5f ) + 0.5f;

            // Threshold brightness between 0 and 1
            if (hsb[2] > 1.0f) {
                hsb[2] = 1.0f;
            }
            else if (hsb[2] < 0.0f) {
                hsb[2] = 0.0f;
            }

            return hsb;
        };

        // Apply the alteration
        this.alterImageHSB(changeContrast);

        this.setImageChanged();
    }

    @Override
    public void brightness(int amount) {

        // Threshold brightness
        if (amount < -1 * BRIGHTNESS_THRESHOLD) {
            amount = -1 * BRIGHTNESS_THRESHOLD;
        }
        else if (amount > BRIGHTNESS_THRESHOLD) {
            amount = BRIGHTNESS_THRESHOLD;
        }

        // Scale to [-1, 1]
        final float changeAmount = amount / (float) BRIGHTNESS_THRESHOLD;

        // Initialize alteration function
        Function<float[], float[]> brightenHSB = hsb -> {
            // Alter brightness
            hsb[2] = hsb[2] + changeAmount;

            // Threshold brightness between 0 and 1
            if (hsb[2] > 1.0f) {
                hsb[2] = 1.0f;
            }
            else if (hsb[2] < 0.0f) {
                hsb[2] = 0.0f;
            }

            return hsb;
        };

        // Apply the alteration
        this.alterImageHSB(brightenHSB);

        this.setImageChanged();
    }

    private void setImageChanged() {
        this.setChanged();
    }

    /**
     * Alters the HSB values of the image.
     *
     * @param alterFunction = the function defining how to alter the HSB values of the image.
     */
    private void alterImageHSB(Function<float[], float[]> alterFunction) {

        // Preallocate the arrays.
        int[] rgb = new int[3];
        float[] hsb = new float[3];

        int width = this.getWidth();
        int height = this.getHeight();

        // For every pixel
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {

                // Get the color from the image.
                this.getPixel(x, y, rgb);

                // Convert to HSB.
                Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);

                // Apply alteration
                hsb = alterFunction.apply(hsb);

                // Convert back to RGB.
                Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
                rgb[0] = c.getRed();
                rgb[1] = c.getGreen();
                rgb[2] = c.getBlue();

                // Set the pixel.
                this.setPixel(x, y, rgb);
            }
        }
    }

    private void applyKernel(IKernel kernel) {

        // Initialize the border policy
        IBorderPolicy borderPolicy = this.getBorderPolicy();

        // Apply the kernel
        kernel.apply(this, borderPolicy);

        this.setImageChanged();
    }

    /**
     * Gets a new border policy for the image.
     *
     * @return a border policy for the image.
     */
    private IBorderPolicy getBorderPolicy() {

        // Make a copy of the image, so anything querying the border policy gets a clean image.
        CS355Image imageCopy = this.copyImage();

        // Initialize border policy
        IBorderPolicy borderPolicy = new PaddedBorder(BORDER_COLOR, imageCopy);

        return borderPolicy;
    }

    /**
     * Makes a deep copy of this image.
     *
     * @return a deep copy of this image.
     */
    private CS355Image copyImage() {

        // Get buffered image
        BufferedImage bi = this.getImage();

        // Initialize new CS355Image from buffered image
        CS355Image imageCopy = new Image();
        imageCopy.setPixels(bi);

        return imageCopy;
    }

}
