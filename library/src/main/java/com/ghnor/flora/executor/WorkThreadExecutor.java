package com.ghnor.flora.executor;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;


/**
 * Created by ghnor on 2017/6/21.
 * email: ghnor.me@gmail.com
 * desc:
 */

public class WorkThreadExecutor implements Executor {

    private static final String TAG = "WorkThreadExecutor";

    private HandlerThread mHandlerThread;

    private Handler mHandler;

    public WorkThreadExecutor() {
        mHandlerThread = new HandlerThread(TAG, Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    private static class Instance {
        private static WorkThreadExecutor sInstance = new WorkThreadExecutor();
    }

    public static WorkThreadExecutor getInstance() {
        return Instance.sInstance;
    }

    public void removeAllTasks() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public void removeTask(@NonNull Runnable command) {
        mHandler.removeCallbacks(command);
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mHandler.post(command);
    }
}
