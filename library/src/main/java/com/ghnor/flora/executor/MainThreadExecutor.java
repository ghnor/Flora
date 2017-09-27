package com.ghnor.flora.executor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by ghnor on 2017/7/8.
 * ghnor.me@gmail.com
 */

public class MainThreadExecutor implements Executor {

    private Handler sMainThreadHandler;

    private MainThreadExecutor() {
        sMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    private static class Instance {
        private static MainThreadExecutor sInstance = new MainThreadExecutor();
    }

    public static MainThreadExecutor getInstance() {
        return Instance.sInstance;
    }

    public static void cancel(@NonNull Runnable command) {
        getInstance().sMainThreadHandler.removeCallbacksAndMessages(command);
    }

    @Override
    public void execute(@NonNull Runnable command) {
        sMainThreadHandler.post(command);
    }
}
