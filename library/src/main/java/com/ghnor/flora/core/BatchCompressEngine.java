package com.ghnor.flora.core;

import com.ghnor.flora.executor.CompressExecutor;
import com.ghnor.flora.spec.CompressSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by ghnor on 2017/9/7.
 * ghnor.me@gmail.com
 */

public abstract class BatchCompressEngine<T extends List<I>, I> extends CompressEngine<T, I, List<String>, String> {

    private List<Future<String>> mFutureList;

    private List<String> mResultList;

    private CompressSpec[] mCompressSpecs;

    public BatchCompressEngine(T t, CompressSpec spec) {
        super(t, spec);
        mCompressSpecs = new CompressSpec[t.size()];
        for (int index = 0; index < mCompressSpecs.length; index++) {
            try {
                mCompressSpecs[index] = (CompressSpec) spec.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        mFutureList = new ArrayList<>();
        mResultList = new ArrayList<>();
    }

    @Override
    protected List<String> impl(boolean sync) throws Exception {
        for (int i = 0; i < source.size() && mTasksContinue; i++) {
            Callable<String> callable = $(source.get(i), mCompressSpecs[i]);

            if (sync) {
                mResultList.add(callable.call());
            } else {
                CompressExecutor.getInstance().setPoolSize(compressSpec.compressThreadNum);
                Future<String> future = CompressExecutor.getExecutor().submit(callable);
                mFutureList.add(future);
            }
        }

        if (sync) {
            return mResultList;
        } else {
            dispatchResultList();
        }
        return null;
    }

    @Override
    public void cancel() {
        super.cancel();
        if (mFutureList != null && !mFutureList.isEmpty()) {
            for (Future future : mFutureList) {
                if (future != null) {
                    future.cancel(true);
                }
            }
        }
    }

    private void dispatchResultList() {
        for (Future<String> future : mFutureList) {
            String compressResult = null;
            try {
                compressResult = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            mResultList.add(compressResult);
        }
        mCallbackDispatcher.dispatch(mResultList);
    }
}
