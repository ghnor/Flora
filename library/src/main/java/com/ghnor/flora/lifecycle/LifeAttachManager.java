package com.ghnor.flora.lifecycle;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;

/**
 * Created by ghnor on 2017/9/10.
 * ghnor.me@gmail.com
 */

public class LifeAttachManager {
    private static final String FRAGMENT_TAG = "LifeListenFragment";

    private LifeAttachManager() {
    }

    public static LifeAttachManager getInstance() {
        return Instance.sInstance;
    }

    /**
     * 开始监听生命周期
     */
    public void attach(Activity activity, OnLifeListener listener) {
        if (!(Looper.myLooper() == Looper.getMainLooper())) {
            return;
        }
        try {
            LifeListenFragment fragment = getLifeListenerFragment(activity);
            LifeListenerManager manager = findLifeListenerManager(fragment);
            manager.addLifeListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void attach(FragmentActivity activity, OnLifeListener listener) {
        if (!(Looper.myLooper() == Looper.getMainLooper())) {
            return;
        }
        try {
            LifeListenSupportFragment fragment = getLifeListenSupportFragment(activity);
            LifeListenerManager manager = findLifeListenerManager(fragment);
            manager.addLifeListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void attach(Fragment fragment, OnLifeListener listener) {
        if (!(Looper.myLooper() == Looper.getMainLooper())) {
            return;
        }
        try {
            LifeListenFragment lFragment = getLifeListenerFragment(fragment);
            LifeListenerManager manager = findLifeListenerManager(lFragment);
            manager.addLifeListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void attach(android.support.v4.app.Fragment fragment, OnLifeListener listener) {
        if (!(Looper.myLooper() == Looper.getMainLooper())) {
            return;
        }
        try {
            LifeListenSupportFragment lFragment = getLifeListenSupportFragment(fragment);
            LifeListenerManager manager = findLifeListenerManager(lFragment);
            manager.addLifeListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 找到指定的Activity绑定的空白Fragment,如果没有则会自动绑定一个
     *
     * @param activity
     * @return
     */
    public LifeListenFragment getLifeListenerFragment(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        return findFragment(fm);
    }

    public LifeListenSupportFragment getLifeListenSupportFragment(FragmentActivity activity) {
        android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
        return findSupportFragment(fm);
    }

    public LifeListenFragment getLifeListenerFragment(Fragment fragment) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            FragmentManager fm = fragment.getChildFragmentManager();
            return findFragment(fm);
        }
        return null;
    }

    public LifeListenSupportFragment getLifeListenSupportFragment(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentManager fm = fragment.getChildFragmentManager();
        return findSupportFragment(fm);
    }

    private LifeListenFragment findFragment(FragmentManager fm) {
        LifeListenFragment current = (LifeListenFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {//没有找到，则新建
            current = new LifeListenFragment();
            fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
        }
        return current;
    }

    private LifeListenSupportFragment findSupportFragment(android.support.v4.app.FragmentManager fm) {
        LifeListenSupportFragment current = (LifeListenSupportFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {//没有找到，则新建
            current = new LifeListenSupportFragment();
            fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
        }
        return current;
    }

    /**
     * 用于获取管理对应Fragment的ActLifeListenerManager
     *
     * @param fragment
     * @return
     */
    private LifeListenerManager findLifeListenerManager(LifeListenFragment fragment) {
        LifeListenerManager manager = fragment.getLifeListenerManager();
        if (null == manager) {
            manager = new LifeListenerManager();
        }
        fragment.setLifeListenerManager(manager);
        return manager;
    }

    private LifeListenerManager findLifeListenerManager(LifeListenSupportFragment fragment) {
        LifeListenerManager manager = fragment.getLifeListenerManager();
        if (null == manager) {
            manager = new LifeListenerManager();
        }
        fragment.setLifeListenerManager(manager);
        return manager;
    }

    private static class Instance {
        private static LifeAttachManager sInstance = new LifeAttachManager();
    }
}
