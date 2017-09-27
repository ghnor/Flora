package com.ghnor.flora;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.ghnor.flora.common.Logger;
import com.ghnor.flora.task.CompressTask;
import com.ghnor.flora.task.CompressTaskBuilder;
import com.ghnor.flora.task.CompressTaskController;

/**
 * Created by ghnor on 2017/7/8.
 * ghnor.me@gmail.com
 */

public class Flora {

    private static String TAG = "Flora";

    public static CompressTaskBuilder with() {
        return new CompressTask().bind();
    }

    public static CompressTaskBuilder with(Activity activity) {
        return new CompressTask().bind(activity);
    }

    public static CompressTaskBuilder with(FragmentActivity activity) {
        return new CompressTask().bind(activity);
    }

    public static CompressTaskBuilder with(Fragment fragment) {
        return new CompressTask().bind(fragment);
    }

    public static CompressTaskBuilder with(android.support.v4.app.Fragment fragment) {
        return new CompressTask().bind(fragment);
    }

    public static CompressTaskBuilder with(String tag) {
        return new CompressTask().bind(tag);
    }

    public static void cancel() {
        CompressTaskController.getInstance().cancel(null);
    }

    public static void cancel(String tag) {
        CompressTaskController.getInstance().cancel(tag);
    }

    public static void debug(boolean isDebug) {
        Logger.debug(isDebug);
    }
}