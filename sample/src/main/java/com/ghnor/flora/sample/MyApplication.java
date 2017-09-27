package com.ghnor.flora.sample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ghnor on 2017/9/14.
 * ghnor.me@gmail.com
 */

public class MyApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}
