package com.ghnor.flora.spec.options;

import static com.ghnor.flora.spec.CompressSpec.DEFAULT_QUALITY;

/**
 * Created by ghnor on 2017/8/27.
 * ghnor.me@gmail.com
 */

public class FileCompressOptions extends BitmapCompressOptions {
    /**
     * The compression quality,value range:0~100.
     * <p>
     * The smaller the value of the higher compression ratio.
     */
    public int quality = DEFAULT_QUALITY;

    /**
     * The max memory maxSize on the hard disk,unit of KB.
     * <p>
     * If the value less than or equal to zero,{@link com.ghnor.flora.spec.CompressSpec} will be automatically set.
     */
    public float maxSize;
}
