package com.ghnor.flora.lifecycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by ghnor on 2017/9/10.
 * ghnor.me@gmail.com
 */

public class LifeListenSupportFragment extends Fragment {
    private LifeListenerManager listenerManager;

    public void setLifeListenerManager(LifeListenerManager manager) {
        listenerManager = manager;
    }

    public LifeListenerManager getLifeListenerManager() {
        return listenerManager;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (listenerManager != null) {
            listenerManager.onActivityCreated(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (listenerManager != null) {
            listenerManager.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listenerManager != null) {
            listenerManager.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (listenerManager != null) {
            listenerManager.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (listenerManager != null) {
            listenerManager.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listenerManager != null) {
            listenerManager.onDestroy();
            listenerManager = null;
        }
    }
}
