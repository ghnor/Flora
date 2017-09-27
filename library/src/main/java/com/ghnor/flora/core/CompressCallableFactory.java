package com.ghnor.flora.core;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import com.ghnor.flora.common.ApplicationLoader;
import com.ghnor.flora.spec.CompressSpec;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ghnor on 2017/7/8.
 * ghnor.me@gmail.com
 */

public class CompressCallableFactory {

    private CompressCallableFactory() {
        throw new RuntimeException("can not be a instance");
    }

    public static class FileCompressCallable extends CompressCallable<File> {
        public FileCompressCallable(File file, CompressSpec compressSpec) {
            super(file, compressSpec);
        }

        @Override
        Bitmap call(File file, BitmapFactory.Options options) {
            return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        }
    }

    public static class FilePathCompressCallable extends CompressCallable<String> {
        public FilePathCompressCallable(String s, CompressSpec compressSpec) {
            super(s, compressSpec);
        }

        @Override
        Bitmap call(String s, BitmapFactory.Options options) {
            return BitmapFactory.decodeFile(s, options);
        }
    }

    public static class FileDescriptorCompressCallable extends CompressCallable<FileDescriptor> {

        public FileDescriptorCompressCallable(FileDescriptor fileDescriptor, CompressSpec compressSpec) {
            super(fileDescriptor, compressSpec);
        }

        @Override
        Bitmap call(FileDescriptor fileDescriptor, BitmapFactory.Options options) {
            return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        }
    }

    public static class ResourceCompressCallable extends CompressCallable<Integer> {

        public ResourceCompressCallable(Integer integer, CompressSpec compressSpec) {
            super(integer, compressSpec);
        }

        @Override
        Bitmap call(Integer integer, BitmapFactory.Options options) {
            Resources resources = ApplicationLoader.getApplication().getResources();

            InputStream is = null;
            Bitmap result;
            try {
                is = resources.openRawResource(integer, new TypedValue());

                result = BitmapFactory.decodeStream(is, null, options);

                return result;
            } catch (Exception e) {
                throw e;
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    is = null;
                }
            }
        }
    }

    public static class InputStreamCompressCallable extends CompressCallable<InputStream> {
        public InputStreamCompressCallable(InputStream inputStream, CompressSpec compressSpec) {
            super(inputStream, compressSpec);
        }

        @Override
        Bitmap call(InputStream inputStream, BitmapFactory.Options options) throws Exception {
            return BitmapFactory.decodeStream(inputStream, null, options);
        }
    }

    public static class BytesCompressCallable extends CompressCallable<byte[]> {
        public BytesCompressCallable(byte[] bytes, CompressSpec compressSpec) {
            super(bytes, compressSpec);
        }

        @Override
        Bitmap call(byte[] bytes, BitmapFactory.Options options) throws Exception {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        }
    }

    public static class BitmapCompressCallable extends CompressCallable<Bitmap> {
        public BitmapCompressCallable(Bitmap bitmap, CompressSpec compressSpec) {
            super(bitmap, compressSpec);
        }

        @Override
        Bitmap call(Bitmap bitmap, BitmapFactory.Options options) throws Exception {
            return matrixCompress(bitmap, options.inSampleSize);
        }
    }

}
