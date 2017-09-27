package com.ghnor.flora.executor;

import com.ghnor.flora.spec.CompressSpec;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ghnor on 2017/7/8.
 * ghnor.me@gmail.com
 */

public class CompressExecutor {

    private ThreadPoolExecutor mThreadPoolExecutor;

    private CompressExecutor() {
        int threads = CompressSpec.DEFAULT_COMPRESS_THREAD_NUM;
        mThreadPoolExecutor = new CompressThreadPool(
                threads,
                threads,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new CompressThreadFactory()
        );
    }

    private static class Instance {
        private static CompressExecutor sInstance = new CompressExecutor();
    }

    public static CompressExecutor getInstance() {
        return Instance.sInstance;
    }

    public static ThreadPoolExecutor getExecutor() {
        return getInstance().mThreadPoolExecutor;
    }

    public void setCorePoolSize(int corePoolSize) {
        if (corePoolSize <= 0 || corePoolSize == mThreadPoolExecutor.getCorePoolSize()) {
            return;
        }
        mThreadPoolExecutor.setCorePoolSize(corePoolSize);
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        if (maximumPoolSize <= 0 || maximumPoolSize == mThreadPoolExecutor.getMaximumPoolSize()) {
            return;
        }
        mThreadPoolExecutor.setMaximumPoolSize(maximumPoolSize);
    }

    public void setPoolSize(int poolSize) {
        setCorePoolSize(poolSize);
        setMaximumPoolSize(poolSize);
    }
}
