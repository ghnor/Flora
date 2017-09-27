package com.ghnor.flora.spec;

import android.graphics.Bitmap;

import com.ghnor.flora.spec.calculation.Calculation;
import com.ghnor.flora.spec.decoration.Decoration;
import com.ghnor.flora.spec.options.FileCompressOptions;

import java.io.File;

/**
 * Created by ghnor on 2017/8/26.
 * ghnor.me@gmail.com
 */

public interface Creator<T> {
    T compressSpec(CompressSpec compressSpec);

    T calculation(Calculation calculation);

    T addDecoration(Decoration decoration);

    T options(FileCompressOptions compressOptions);

    T bitmapConfig(Bitmap.Config config);

    T maxFileSize(float maxSize);

    T compressTaskNum(int n);

    T safeMemory(int safeMemory);

    T diskDirectory(File dir);
}
