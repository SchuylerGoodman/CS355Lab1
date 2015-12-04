package cs355.model.image.kernel;

import cs355.model.image.CS355Image;

/**
 * Created by BaronVonBaerenstein on 12/3/2015.
 */
public interface IKernel {

    /**
     * Apply the kernel to an image, altering it.
     *
     * @param image = the image to alter.
     * @param borderPolicy = a border policy for getting values outside range of image.
     */
    void apply(CS355Image image, IBorderPolicy borderPolicy);
}
