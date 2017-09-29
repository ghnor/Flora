package com.ghnor.flora.spec;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.ghnor.flora.core.BatchCompressEngine;
import com.ghnor.flora.core.SingleCompressEngine;

import java.io.File;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ghnor on 2017/8/26.
 * ghnor.me@gmail.com
 */

public interface Loader {
    SingleCompressEngine<String> load(String filePath);

    BatchCompressEngine<List<String>, String> load(String... filePath);

    SingleCompressEngine<File> load(File file);

    BatchCompressEngine<List<File>, File> load(File... file);

    SingleCompressEngine<FileDescriptor> load(FileDescriptor fileDescriptor);

    BatchCompressEngine<List<FileDescriptor>, FileDescriptor> load(FileDescriptor... fileDescriptors);

    SingleCompressEngine<Integer> load(@DrawableRes int resId);

    BatchCompressEngine<List<Integer>, Integer> load(@DrawableRes int... resId);

    SingleCompressEngine<InputStream> load(InputStream is);

    BatchCompressEngine<List<InputStream>, InputStream> load(InputStream... is);

    SingleCompressEngine<byte[]> load(byte[] bytes);

    BatchCompressEngine<List<byte[]>, byte[]> load(byte[]... bytes);

    SingleCompressEngine<Bitmap> load(Bitmap bitmap);

    BatchCompressEngine<List<Bitmap>, Bitmap> load(Bitmap... bitmap);

    <T extends List<I>, I> BatchCompressEngine<T, I> load(T list);
}
