package com.ghnor.flora.spec;

import android.graphics.Bitmap;

import com.ghnor.flora.spec.calculation.Calculation;
import com.ghnor.flora.spec.calculation.DefaultCalculation;
import com.ghnor.flora.spec.decoration.Decoration;
import com.ghnor.flora.spec.options.FileCompressOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghnor on 2017/6/21.
 * email: ghnor.me@gmail.com
 * desc:
 */

public class CompressSpec implements Cloneable {
    public static final String DEFAULT_DISK_CACHE_DIR = "compress_disk_cache";
    public static final int DEFAULT_QUALITY = 50;
    public static final Bitmap.Config DEFAULT_BITMAP_CONFIG = Bitmap.Config.RGB_565;
    public static final int DEFAULT_DECODE_BUFFER_SIZE = 16 * 1024;
    public static final int DEFAULT_IN_SAMPLE_SIZE = 1;
    public static final int DEFAULT_SAFE_MEMORY = 2;
    private static final String TAG = "CompressSpec";
    public static int DEFAULT_COMPRESS_THREAD_NUM;

    static {
        DEFAULT_COMPRESS_THREAD_NUM = Runtime.getRuntime().availableProcessors() + 1;
    }

    public volatile Calculation calculation;

    public List<Decoration> decorations;

    public FileCompressOptions options;

    public int compressThreadNum;

    public int safeMemory;

    public File dir;

    private CompressSpec() {
        calculation = new DefaultCalculation();
        decorations = new ArrayList<>();
        options = new FileCompressOptions();
        compressThreadNum = DEFAULT_COMPRESS_THREAD_NUM;
        safeMemory = DEFAULT_SAFE_MEMORY;
    }

    public static CompressSpec newInstance(CompressSpec compressSpec) {
        return compressSpec;
    }

    public static CompressSpec newInstance() {
        return new CompressSpec();
    }

    public static CompressSpec getInstance(CompressSpec compressSpec) {
        ConfigurationHolder.sInstance = compressSpec;
        return ConfigurationHolder.sInstance;
    }

    public static CompressSpec getInstance() {
        return ConfigurationHolder.sInstance;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        this.options = (FileCompressOptions) options.clone();
        return super.clone();
    }

    private static class ConfigurationHolder {
        private static CompressSpec sInstance = new CompressSpec();
    }
}
