package com.ghnor.flora.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.ghnor.flora.common.BitmapOptionsCompat;
import com.ghnor.flora.common.Degrees;
import com.ghnor.flora.common.FileUtil;
import com.ghnor.flora.common.Logger;
import com.ghnor.flora.spec.CompressSpec;
import com.ghnor.flora.spec.decoration.Decoration;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by ghnor on 2017/7/8.
 * ghnor.me@gmail.com
 */

public abstract class CompressCallable<T> implements Callable<String> {

    CompressSpec mCompressSpec;
    T mT;

    public CompressCallable(T t, CompressSpec compressSpec) {
        this.mT = t;
        this.mCompressSpec = compressSpec;
    }

    @Override
    public String call() throws Exception {
        BitmapFactory.Options decodeOptions = compressPre(mCompressSpec);
        Bitmap bm = call(mT, decodeOptions);
        bm = Degrees.handle(bm, mT);
        bm = compressOver(bm, mCompressSpec);
        return compress(bm, mCompressSpec);
    }

    protected BitmapFactory.Options compressPre(CompressSpec compressSpec) {
        BitmapFactory.Options decodeOptions = BitmapOptionsCompat.getDefaultDecodeOptions();
        decodeOptions.inPreferredConfig = compressSpec.options.bitmapConfig;
        decodeOptions.inSampleSize = compressSpec.options.inSampleSize;
        return decodeOptions;
    }

    protected Bitmap compressOver(Bitmap bitmap, CompressSpec compressSpec) {
        for (Decoration decoration : compressSpec.decorations) {
            bitmap = decoration.onDraw(bitmap);
        }
        return bitmap;
    }

    protected Bitmap matrixCompress(Bitmap bitmap, int inSampleSize) {
        if (bitmap == null) return null;

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        if (inSampleSize >= 1) {
            Matrix matrix = new Matrix();
            matrix.postScale(1.0f / (float) inSampleSize, 1.0f / (float) inSampleSize);
            Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
            return result;
        } else {
            return bitmap;
        }
    }

    protected String compress(Bitmap bitmap, CompressSpec compressSpec) {
        int quality = compressSpec.options.quality;
        String outfile = null;
        float size = compressSpec.options.maxSize;

        if (quality < 0 || quality > 100)
            quality = CompressSpec.DEFAULT_QUALITY;

        if (bitmap.hasAlpha()) {
            outfile = FileUtil.generateCompressOutfileFormatPNG(compressSpec.dir, null).getAbsolutePath();
        } else {
            outfile = FileUtil.generateCompressOutfileFormatJPEG(compressSpec.dir, null).getAbsolutePath();
        }

        boolean isSuccess = compress(bitmap, outfile, quality);

        if (size > 0 && isSuccess) {
            float outfileSize = (float) FileUtil.getSizeInBytes(outfile) / (float) 1024;
            while (outfileSize > size) {
                if (quality <= 25)
                    break;
                quality -= 5;
                isSuccess = compress(bitmap, outfile, quality);
                if (!isSuccess)
                    break;
                outfileSize = (float) FileUtil.getSizeInBytes(outfile) / (float) 1024;
            }
        }

        Logger.i("final compress quality: " + quality);

        if (bitmap != mT) {
            bitmap.recycle();
            bitmap = null;
            System.gc();
            System.runFinalization();
        }

        return outfile;
    }

    protected boolean compress(Bitmap bitmap, String outfile, int quality) {
        if (bitmap == null || bitmap.isRecycled())
            return false;

        if (bitmap.hasAlpha()) {
            return compress(bitmap, outfile, quality, Bitmap.CompressFormat.PNG);
        } else {
            return compress(bitmap, outfile, quality, Bitmap.CompressFormat.JPEG);
        }
    }

    protected boolean compress(Bitmap bitmap, String outfile, int quality, Bitmap.CompressFormat format) {
        boolean isSuccess = false;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outfile);
            isSuccess = bitmap.compress(format, quality, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            //avoid v6.0+ occur crash without permission
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                fos = null;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    abstract Bitmap call(T t, BitmapFactory.Options options) throws Exception;
}
