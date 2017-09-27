package com.ghnor.flora.common;

import android.app.Application;

import java.lang.reflect.Method;

/**
 * Apache License
 *
 * Version 2.0, January 2004
 * http://www.apache.org/licenses/
 *
 * Copyright 2017 郑晓勇
 */

public class ApplicationLoader {

    private static Application sApplication;

    private ApplicationLoader() {
        sApplication = loadApplication();
    }

    private static ApplicationLoader getInstance() {
        return ApplicationLoaderHolder.sInstance;
    }

    private static Application loadApplication() {
        Application application = null;
        Method method;
        try {
            method = Class.forName("android.app.AppGlobals").getDeclaredMethod("getInitialApplication");
            method.setAccessible(true);
            application = (Application) method.invoke(null);
        } catch (Exception e) {
            try {
                method = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication");
                method.setAccessible(true);
                application = (Application) method.invoke(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return application;
    }

    public static Application getApplication() {
        getInstance();
        return sApplication != null ? sApplication : loadApplication();
    }

    private static class ApplicationLoaderHolder {
        private static ApplicationLoader sInstance = new ApplicationLoader();
    }

}
