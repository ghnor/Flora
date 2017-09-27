package com.ghnor.flora.core;

import com.ghnor.flora.executor.CompressExecutor;
import com.ghnor.flora.spec.CompressSpec;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by ghnor on 2017/9/7.
 * ghnor.me@gmail.com
 */

public abstract class SingleCompressEngine<T> extends CompressEngine<T, T, String, String> {
    private Future<String> mFuture;

    public SingleCompressEngine(T t, CompressSpec spec) {
        super(t, spec);
    }

    @Override
    protected String impl(boolean sync) throws Exception {
        Callable<String> callable = $(source, compressSpec);
        if (sync) {
            return callable.call();
        } else {
            CompressExecutor.getInstance().setPoolSize(compressSpec.compressThreadNum);
            mFuture = CompressExecutor.getExecutor().submit(callable);
            dispatchResult();
        }
        return null;
    }

    @Override
    public void cancel() {
        super.cancel();
        if (mFuture != null) {
            mFuture.cancel(true);
        }
    }

    private void dispatchResult() {
        String compressResult = null;
        try {
            compressResult = mFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        mCallbackDispatcher.dispatch(compressResult);
    }
}
