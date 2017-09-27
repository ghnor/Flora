package com.ghnor.flora.common;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by ghnor on 2017/6/24.
 * ghnor.me@gmail.com
 */

public class FileUtil {

    private static final String DEFAULT_FILE_COMPRESS_DIRECTORY_NAME = "image-compressor";

    private static final Random RANDOM = new Random();

    private static final ThreadLocal<DateFormat> FILE_SUFFIX_DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
        }
    };

    public static File generateCompressOutfileFormatJPEG(File directory, String name) {
        if (directory == null) {
            directory = getDefaultFileCompressDirectory();
        }
        if (name == null || TextUtils.isEmpty(name)) {
            String suffix = getDateFormat().format(new Date(System.currentTimeMillis()));
            int seed = RANDOM.nextInt(1000);
            name = seed + "-" + suffix + ".jpg";
        }
        return new File(directory, name);
    }

    public static File generateCompressOutfileFormatPNG(File directory, String name) {
        if (directory == null) {
            directory = getDefaultFileCompressDirectory();
        }
        if (name == null || TextUtils.isEmpty(name)) {
            String suffix = getDateFormat().format(new Date(System.currentTimeMillis()));
            int seed = RANDOM.nextInt(1000);
            name = seed + "-" + suffix + ".png";
        }
        return new File(directory, name);
    }

    public static File getDefaultFileCompressDirectory() {
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            file = ApplicationLoader.getApplication().getExternalFilesDir(null);
        }
        file = file == null ? ApplicationLoader.getApplication().getFilesDir() : file;

        file = new File(file.getParentFile(), DEFAULT_FILE_COMPRESS_DIRECTORY_NAME);
        if (!file.exists())
            file.mkdirs();
        return file;
    }

    public static long getSizeInBytes(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return 0L;
        return getSizeInBytes(new File(filePath));
    }

    public static long getSizeInBytes(File file) {
        if (file == null || !file.exists() || !file.isFile())
            return 0L;
        return file.length();
    }

    public static long getSizeInBytes(InputStream is) {
        if (is == null)
            return 0L;
        try {
            return is.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static DateFormat getDateFormat() {
        return FILE_SUFFIX_DATE_FORMAT_THREAD_LOCAL.get();
    }

    public static File[] wrap(String[] filePaths) {
        if (filePaths == null || filePaths.length == 0)
            return null;
        File[] files = new File[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            String filePath = filePaths[i];
            files[i] = TextUtils.isEmpty(filePath) ? new File("") : new File(filePath);
        }
        return files;
    }

    public static boolean clearDirectory(File dir) {
        if (dir == null || !dir.isDirectory() || !dir.exists())
            return false;
        File[] files = dir.listFiles();
        int length = files.length;
        for (int i = 0; i < length; i++) {
            File file = files[i];
            if (file == null)
                continue;
            if (file.isFile() && file.exists()) {
                boolean result = file.delete();
                Logger.i(file.getName() + (result ? " delete success!" : " delete failed!"));
                continue;
            }
            if (file.isDirectory() && file.exists()) {
                clearDirectory(file);
            }
        }
        return true;
    }

    public static boolean fileIsExist(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return false;
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    public static boolean fileCanRead(String filePath) {
        if (filePath == null)
            return false;
        final File file = new File(filePath);
        return file.exists() && file.canRead();
    }

    public static boolean fileCanRead(File file) {
        return file != null && file.exists() && file.canRead();
    }

    public static boolean fileCanWrite(String filePath) {
        if (filePath == null)
            return false;
        final File file = new File(filePath);
        return file.exists() && file.canWrite();
    }

    public static boolean fileCanWrite(File file) {
        return file != null && file.exists() && file.canWrite();
    }

    public static boolean isDirectory(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return true;
        File file = new File(filePath);
        return file.exists() && file.isDirectory();
    }
}
