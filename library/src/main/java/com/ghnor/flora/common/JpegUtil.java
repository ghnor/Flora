package com.ghnor.flora.common;

import android.text.TextUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by ghnor on 2017/6/23.
 * email: ghnor.me@gmail.com
 * desc:
 */

public class JpegUtil {

    private static final String[] JPEG_FORMAT_SUFFIX = new String[]{
            ".jpg", ".jpeg", ".JPG", ".JPEG"
    };

    private static final byte[] JPEG_SIGNATURE = new byte[]{
            (byte) 0xff, (byte) 0xd8, (byte) 0xff
    };

    private static final int JPEG_SIGNATURE_SIZE = 3;

    /**
     * @param filePath
     * @return
     */
    public static boolean isJpegFormat(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return false;
        for (int i = 0; i < JPEG_FORMAT_SUFFIX.length; i++) {
            if (filePath.endsWith(JPEG_FORMAT_SUFFIX[i]))
                return true;
        }
        return false;
    }

    public static boolean isJpegFormat(File file) {
        if (file == null) return false;
        return isJpegFormat(file.getAbsolutePath());
    }

    public static boolean isJpegFormat(InputStream is) {
        boolean isJpeg = false;
        if (is == null) return isJpeg;
        is.mark(JPEG_SIGNATURE_SIZE);
        byte[] signatureBytes = new byte[JPEG_SIGNATURE_SIZE];
        try {
            if (is.read(signatureBytes) != JPEG_SIGNATURE_SIZE)
                throw new RuntimeException("no more data.");
            isJpeg = Arrays.equals(JPEG_SIGNATURE, signatureBytes);
            is.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isJpeg;
    }

    public static boolean isJpegFormat(byte[] data) {
        boolean isJpeg = false;
        if (data == null || data.length < 3)
            return isJpeg;
        byte[] signatureBytes = new byte[]{
                data[0], data[1], data[2]
        };
        isJpeg = Arrays.equals(JPEG_SIGNATURE, signatureBytes);
        return isJpeg;
    }

}
