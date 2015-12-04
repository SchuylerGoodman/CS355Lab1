package cs355.model.image.kernel;

/**
 * Created by BaronVonBaerenstein on 12/4/2015.
 */
public class EdgeDetection extends AlterRGB {
    
    @Override
    protected int getKernelRadius() {
        return 0;
    }

    @Override
    protected int[] alterPixelRGB(int[] reds, int[] greens, int[] blues) {
        return new int[0];
    }
}
