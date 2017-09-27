package com.ghnor.flora.common;

import android.util.Log;

/**
 * Created by ghnor on 2017/6/24.
 * ghnor.me@gmail.com
 */

public class Logger {

    private static final String TAG = "image compressor logger";

    private static boolean mIsDebug = false;

    public static void debug(boolean isDebug) {
        mIsDebug = isDebug;
    }

    public static boolean isDebug() {
        return mIsDebug;
    }

    public static void e(String message) {
        if (isDebug())
            Log.e(TAG, message);
    }

    public static void i(String message) {
        if (isDebug())
            Log.i(TAG, message);
    }
}
