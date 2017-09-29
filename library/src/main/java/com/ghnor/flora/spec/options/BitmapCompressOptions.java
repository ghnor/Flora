package com.ghnor.flora.spec.options;

import android.graphics.Bitmap;

import static com.ghnor.flora.spec.CompressSpec.DEFAULT_BITMAP_CONFIG;
import static com.ghnor.flora.spec.CompressSpec.DEFAULT_IN_SAMPLE_SIZE;

/**
 * Created by ghnor on 2017/8/27.
 * ghnor.me@gmail.com
 */

public class BitmapCompressOptions extends CompressOptions {
    /**
     * By default,using ARGB_8888.
     * <p>
     * You can also consider using RGB_565,it can save half of memory maxSize.
     */
    public Bitmap.Config bitmapConfig = DEFAULT_BITMAP_CONFIG;

    /**
     * By default,using 60.
     * <p>
     *
     * @see android.graphics.BitmapFactory
     */
    public int inSampleSize = DEFAULT_IN_SAMPLE_SIZE;
}
