package com.ghnor.flora.spec.calculation;

/**
 * Created by zr on 2017/9/14.
 * desc:
 */

public abstract class Calculation {
    public int calculateInSampleSize(int srcWidth, int srcHeight) {
        return 1;
    }

    public int calculateQuality(int srcWidth, int srcHeight, int targetWidth, int targetHeight) {
        return 75;
    }
}
