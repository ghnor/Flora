package com.ghnor.flora.core;

import android.graphics.BitmapFactory;

import com.ghnor.flora.callback.Callback;
import com.ghnor.flora.callback.CallbackDispatcher;
import com.ghnor.flora.callback.DefaultCallbackDispatcher;
import com.ghnor.flora.common.BitmapOptionsCompat;
import com.ghnor.flora.common.Logger;
import com.ghnor.flora.common.MemoryUtil;
import com.ghnor.flora.executor.WorkThreadExecutor;
import com.ghnor.flora.spec.CompressComponent;
import com.ghnor.flora.spec.CompressSpec;

import java.util.concurrent.Callable;

/**
 * Created by ghnor on 2017/9/4.
 * ghnor.me@gmail.com
 */

public abstract class CompressEngine<T, I, R, C> extends CompressComponent<T> implements Runnable {

    protected CallbackDispatcher<R> mCallbackDispatcher;

    protected boolean mTasksContinue = true;

    public CompressEngine(T t, CompressSpec spec) {
        source = t;
        compressSpec = spec;
    }

    public R compressSync() throws Exception {
        return impl(true);
    }

    public void compress(Callback<R> callback) {
        mCallbackDispatcher = new DefaultCallbackDispatcher<>(callback);
        WorkThreadExecutor.getInstance().execute(this);
    }

    Callable<C> $(I i, CompressSpec spec) throws Exception {
        BitmapFactory.Options decodeBoundsOptions = BitmapOptionsCompat.getDefaultDecodeBoundsOptions();

        getDecodeOptions(i, decodeBoundsOptions);

        spec.options.inSampleSize =
                spec.calculation.calculateInSampleSize(
                        decodeBoundsOptions.outWidth,
                        decodeBoundsOptions.outHeight);
        Logger.i("inSampleSize-->" + spec.options.inSampleSize);
        spec.options.quality =
                spec.calculation.calculateQuality(
                        decodeBoundsOptions.outWidth,
                        decodeBoundsOptions.outHeight,
                        decodeBoundsOptions.outWidth / spec.options.inSampleSize,
                        decodeBoundsOptions.outHeight / spec.options.inSampleSize);
        Logger.i("quality-->" + spec.options.quality);
        while (!MemoryUtil.memoryEnough(
                decodeBoundsOptions.outWidth / spec.options.inSampleSize,
                decodeBoundsOptions.outHeight / spec.options.inSampleSize,
                decodeBoundsOptions.inPreferredConfig,
                spec.safeMemory));

        Callable<C> callable = getCallable(i, spec);
        return callable;
    }

    @Override
    public void run() {
        try {
            if (mTasksContinue) {
                System.gc();
                System.runFinalization();
                impl(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        mTasksContinue = false;
        WorkThreadExecutor.getInstance().removeTask(this);
        if (mCallbackDispatcher != null) {
            mCallbackDispatcher.cancel();
        }
    }

    protected abstract BitmapFactory.Options getDecodeOptions(I i, BitmapFactory.Options options) throws Exception;

    protected abstract Callable<C> getCallable(I i, CompressSpec spec) throws Exception;

    protected abstract R impl(boolean sync) throws Exception;
}
