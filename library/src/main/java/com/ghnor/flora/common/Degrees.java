package com.ghnor.flora.common;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.TypedValue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Apache License
 *
 * Version 2.0, January 2004
 * http://www.apache.org/licenses/
 *
 * Copyright 2017 郑晓勇
 */

public class Degrees {
    public static <T> Bitmap handle(Bitmap bitmap, T t) {
        Bitmap bm = null;
        if (t instanceof File) {
            bm = handle(bitmap, (File) t);
        } else if (t instanceof String) {
            bm = handle(bitmap, (String) t);
        } else if (t instanceof FileDescriptor) {
            bm = handle(bitmap, (FileDescriptor) t);
        } else if (t instanceof InputStream) {
            bm = handle(bitmap, (InputStream) t);
        } else if (t instanceof byte[]) {
            bm = handle(bitmap, (byte[]) t);
        } else if (t instanceof Integer) {
            bm = handle(bitmap, (Integer) t);
        } else {
            bm = bitmap;
        }
        return bm;
    }

    public static Bitmap handle(Bitmap bitmap, File file) {
        return handle(bitmap, file.getAbsolutePath());
    }

    public static Bitmap handle(Bitmap bitmap, String filePath) {
        if (JpegUtil.isJpegFormat(filePath)) {
            int orientation = ExifCompat.getOrientation(filePath);
            bitmap = BitmapUtil.rotateBitmap(bitmap, orientation);
        }
        return bitmap;
    }

    public static Bitmap handle(Bitmap bitmap, FileDescriptor fileDescriptor) {
        if (JpegUtil.isJpegFormat(new FileInputStream(fileDescriptor))) {
            int orientation = ExifCompat.getOrientation(new FileInputStream(fileDescriptor));
            bitmap = BitmapUtil.rotateBitmap(bitmap, orientation);
        }
        return bitmap;
    }

    public static Bitmap handle(Bitmap bitmap, InputStream is) {
        try {
            is.reset();
            if (JpegUtil.isJpegFormat(is)) {
                is.reset();
                int orientation = ExifCompat.getOrientation(is);
                bitmap = BitmapUtil.rotateBitmap(bitmap, orientation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                is = null;
            }
        }
        return bitmap;
    }

    public static Bitmap handle(Bitmap bitmap, byte[] data) {
        if (JpegUtil.isJpegFormat(data)) {
            int orientation = ExifCompat.getOrientation(data);
            bitmap = BitmapUtil.rotateBitmap(bitmap, orientation);
        }
        return bitmap;
    }

    public static Bitmap handle(Bitmap bitmap, Integer resId) {
        InputStream is = null;
        Resources resources = ApplicationLoader.getApplication().getResources();
        try {
            is = resources.openRawResource(resId, new TypedValue());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.close();

            if (JpegUtil.isJpegFormat(os.toByteArray())) {
                int orientation = ExifCompat.getOrientation(os.toByteArray());
                bitmap = BitmapUtil.rotateBitmap(bitmap, orientation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    is = null;
                }
            }
        }
        return bitmap;
    }

}
