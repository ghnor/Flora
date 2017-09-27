package com.ghnor.flora.lifecycle;

import android.os.Bundle;

/**
 * Created by ghnor on 2017/9/10.
 * ghnor.me@gmail.com
 */

public interface OnLifeListener {
    public void onCreate(Bundle bundle);

    public void onStart();

    public void onResume();

    public void onPause();

    public void onStop();

    public void onDestroy();
}
