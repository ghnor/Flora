package com.ghnor.flora.task;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.ghnor.flora.common.MD5Util;
import com.ghnor.flora.lifecycle.LifeAttachManager;
import com.ghnor.flora.lifecycle.SimpleOnLifeListener;

/**
 * Created by ghnor on 2017/9/13.
 * ghnor.me@gmail.com
 */

public class CompressTask {
    private StringBuilder mWrapperTag = new StringBuilder();

    public CompressTaskBuilder bind() {
        return new CompressTaskBuilder(this);
    }

    public CompressTaskBuilder bind(Activity activity) {
        final String tag = activity.toString();
        ;
        mWrapperTag.append(MD5Util.md5(tag));
        LifeAttachManager.getInstance().attach(activity, new SimpleOnLifeListener() {
            @Override
            public void onDestroy() {
                super.onDestroy();
                CompressTaskController.getInstance().cancel(tag);
            }
        });
        return bind();
    }

    public CompressTaskBuilder bind(FragmentActivity activity) {
        final String tag = activity.toString();
        mWrapperTag.append(MD5Util.md5(tag));
        LifeAttachManager.getInstance().attach(activity, new SimpleOnLifeListener() {
            @Override
            public void onDestroy() {
                super.onDestroy();
                CompressTaskController.getInstance().cancel(tag);
            }
        });
        return bind();
    }

    public CompressTaskBuilder bind(Fragment fragment) {
        final String tag = fragment.toString();
        mWrapperTag.append(MD5Util.md5(tag));
        LifeAttachManager.getInstance().attach(fragment, new SimpleOnLifeListener() {
            @Override
            public void onDestroy() {
                super.onDestroy();
                CompressTaskController.getInstance().cancel(tag);
            }
        });
        return bind();
    }

    public CompressTaskBuilder bind(android.support.v4.app.Fragment fragment) {
        final String tag = fragment.toString();
        mWrapperTag.append(MD5Util.md5(tag));
        LifeAttachManager.getInstance().attach(fragment, new SimpleOnLifeListener() {
            @Override
            public void onDestroy() {
                super.onDestroy();
                CompressTaskController.getInstance().cancel(tag);
            }
        });
        return bind();
    }

    public CompressTaskBuilder bind(String tag) {
        mWrapperTag.append(MD5Util.md5(tag));
        return bind();
    }

    public String obtainTag() {
        return mWrapperTag.toString();
    }
}
