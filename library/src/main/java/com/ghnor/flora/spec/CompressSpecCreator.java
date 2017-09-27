package com.ghnor.flora.spec;

import android.graphics.Bitmap;

import com.ghnor.flora.spec.calculation.Calculation;
import com.ghnor.flora.spec.decoration.Decoration;
import com.ghnor.flora.spec.options.FileCompressOptions;

import java.io.File;

/**
 * Created by ghnor on 2017/7/8.
 * ghnor.me@gmail.com
 */

public class CompressSpecCreator implements Creator<CompressSpecCreator> {

    private CompressSpec mCompressSpec;

    public CompressSpecCreator() {
        mCompressSpec = CompressSpec.newInstance();
    }

    public CompressSpec create() {
        return mCompressSpec;
    }

    @Override
    public CompressSpecCreator compressSpec(CompressSpec compressSpec) {
        return null;
    }

    @Override
    public CompressSpecCreator calculation(Calculation calculation) {
        mCompressSpec.calculation = calculation;
        return this;
    }

    @Override
    public CompressSpecCreator addDecoration(Decoration decoration) {
        mCompressSpec.decorations.add(decoration);
        return this;
    }

    @Override
    public CompressSpecCreator options(FileCompressOptions compressOptions) {
        mCompressSpec.options = compressOptions;
        return this;
    }

    @Override
    public CompressSpecCreator bitmapConfig(Bitmap.Config config) {
        mCompressSpec.options.bitmapConfig = config;
        return this;
    }

    @Override
    public CompressSpecCreator maxFileSize(float maxSize) {
        mCompressSpec.options.maxSize = maxSize;
        return this;
    }

    @Override
    public CompressSpecCreator compressTaskNum(int n) {
        mCompressSpec.compressThreadNum = n;
        return this;
    }

    @Override
    public CompressSpecCreator safeMemory(int safeMemory) {
        mCompressSpec.safeMemory = safeMemory;
        return this;
    }

    @Override
    public CompressSpecCreator diskDirectory(File dir) {
        mCompressSpec.dir = dir;
        return this;
    }
}
