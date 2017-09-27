package com.ghnor.flora.task;

import com.ghnor.flora.common.MD5Util;
import com.ghnor.flora.core.CompressEngine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ghnor on 2017/9/12.
 * ghnor.me@gmail.com
 */

public class CompressTaskController {

    private Map<String, CompressEngine> mEngineMap;

    public CompressTaskController() {
        mEngineMap = new ConcurrentHashMap<>();
    }

    public static CompressTaskController getInstance() {
        return Instance.sInstance;
    }

    public void addTask(String tag, CompressEngine engine) {
        if (tag != null && !tag.isEmpty()) {
            mEngineMap.put(tag, engine);
        }
    }

    public void removeTask(String tag) {
        for (Map.Entry<String, CompressEngine> entry : mEngineMap.entrySet()) {
            if (entry.getKey().contains(tag)) {
                mEngineMap.remove(entry.getKey());
            }
        }
    }

    public void clearTask() {
        mEngineMap.clear();
    }

    public void cancel(Object tag) {
        if (tag == null) {
            for (Map.Entry<String, CompressEngine> entry : mEngineMap.entrySet()) {
                entry.getValue().cancel();
            }
            clearTask();
        } else {
            String md5Tag = MD5Util.md5(tag.toString());
            for (Map.Entry<String, CompressEngine> entry : mEngineMap.entrySet()) {
                if (entry.getKey().contains(md5Tag)) {
                    entry.getValue().cancel();
                    removeTask(entry.getKey());
                }
            }
        }
    }

    private static class Instance {
        private static CompressTaskController sInstance = new CompressTaskController();
    }
}
