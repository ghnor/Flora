package com.ghnor.flora.spec.decoration;

import android.graphics.Bitmap;

/**
 * Created by zr on 2017/9/14.
 * desc:
 */

public abstract class Decoration {

    public Bitmap onDraw(Bitmap bitmap) {
        return bitmap;
    }
}
