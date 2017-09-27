package com.ghnor.flora.callback;

import com.ghnor.flora.executor.MainThreadExecutor;

/**
 * Created by ghnor on 2017/6/21.
 * email: ghnor.me@gmail.com
 * desc:
 */

public class DefaultCallbackDispatcher<T> implements CallbackDispatcher<T> {

    private static final String TAG = "DefaultCallback";

    private Callback<T> mCallback;

    private Runnable mRunnable;

    public DefaultCallbackDispatcher(Callback<T> callback) {
        this.mCallback = callback;
    }

    @Override
    public void dispatch(final T t) {
        if (mCallback == null) return;

        mRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mCallback.callback(t);
                    cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        MainThreadExecutor.getInstance().execute(mRunnable);
    }

    @Override
    public void cancel() {
        MainThreadExecutor.getInstance().cancel(mRunnable);
        mRunnable = null;
    }
}
