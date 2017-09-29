package com.ghnor.flora.task;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.ghnor.flora.core.BatchCompressEngine;
import com.ghnor.flora.core.CompressEngineFactory;
import com.ghnor.flora.core.SingleCompressEngine;
import com.ghnor.flora.spec.CompressComponent;
import com.ghnor.flora.spec.CompressSpec;
import com.ghnor.flora.spec.CompressSpecCreator;
import com.ghnor.flora.spec.Creator;
import com.ghnor.flora.spec.Loader;
import com.ghnor.flora.spec.calculation.Calculation;
import com.ghnor.flora.spec.decoration.Decoration;
import com.ghnor.flora.spec.options.FileCompressOptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ghnor on 2017/7/8.
 * ghnor.me@gmail.com
 */

public class CompressTaskBuilder extends CompressComponent implements Creator<CompressTaskBuilder>, Loader {

    private CompressTask mCompressTask;

    private CompressSpecCreator mCompressSpecCreator;

    public CompressTaskBuilder() {
        mCompressSpecCreator = new CompressSpecCreator();
    }

    public CompressTaskBuilder(CompressTask compressTask) {
        this();
        mCompressTask = compressTask;
    }

    /**
     * file path
     *
     * @param filePath
     * @return
     */
    @Override
    public SingleCompressEngine<String> load(String filePath) {
        SingleCompressEngine<String> engine = CompressEngineFactory.buildFilePathSingle(filePath, mCompressSpecCreator.create());
        CompressTaskController.getInstance().addTask(mCompressTask.obtainTag(), engine);
        return engine;
    }

    @Override
    public BatchCompressEngine<List<String>, String> load(String... filePath) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, filePath);
        return load(list);
    }

    /**
     * file
     *
     * @param file
     * @return
     */
    @Override
    public SingleCompressEngine<File> load(File file) {
        SingleCompressEngine<File> engine = CompressEngineFactory.buildFileSingle(file, mCompressSpecCreator.create());
        CompressTaskController.getInstance().addTask(mCompressTask.obtainTag(), engine);
        return engine;
    }

    @Override
    public BatchCompressEngine<List<File>, File> load(File... file) {
        List<File> list = new ArrayList<>();
        Collections.addAll(list, file);
        return load(list);
    }

    /**
     * FileDescriptor
     *
     * @param fileDescriptor
     * @return
     */
    @Override
    public SingleCompressEngine<FileDescriptor> load(FileDescriptor fileDescriptor) {
        SingleCompressEngine<FileDescriptor> engine = CompressEngineFactory.buildFileDescriptorSingle(fileDescriptor, mCompressSpecCreator.create());
        CompressTaskController.getInstance().addTask(mCompressTask.obtainTag(), engine);
        return engine;
    }

    @Override
    public BatchCompressEngine<List<FileDescriptor>, FileDescriptor> load(FileDescriptor... fileDescriptors) {
        List<FileDescriptor> list = new ArrayList<>();
        Collections.addAll(list, fileDescriptors);
        return load(list);
    }

    /**
     * @param resId
     * @return
     */
    @Override
    public SingleCompressEngine<Integer> load(@DrawableRes int resId) {
        SingleCompressEngine<Integer> engine = CompressEngineFactory.buildResourceSingle(resId, mCompressSpecCreator.create());
        CompressTaskController.getInstance().addTask(mCompressTask.obtainTag(), engine);
        return engine;
    }

    @Override
    public BatchCompressEngine<List<Integer>, Integer> load(@DrawableRes int... resId) {
        Integer[] resInteger = new Integer[resId.length];
        for (int index = 0; index < resId.length; index++) {
            resInteger[index] = resId[index];
        }
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, resInteger);
        return load(list);
    }

    /**
     * InputStream
     *
     * @param is
     * @return
     */
    @Override
    public SingleCompressEngine<InputStream> load(InputStream is) {
        ByteArrayOutputStream baos = null;
        InputStream inputStream = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int len;
            while ((len = is.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            inputStream = new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SingleCompressEngine<InputStream> engine = CompressEngineFactory.buildInputStreamSingle(inputStream, mCompressSpecCreator.create());
        CompressTaskController.getInstance().addTask(mCompressTask.obtainTag(), engine);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is = null;
        }
        try {
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos = null;
        }
        return engine;
    }

    @Override
    public BatchCompressEngine<List<InputStream>, InputStream> load(InputStream... is) {
        List<InputStream> list = new ArrayList<>();
        Collections.addAll(list, is);
        return load(list);
    }

    /**
     * byte[]
     *
     * @param bytes
     * @return
     */
    @Override
    public SingleCompressEngine<byte[]> load(byte[] bytes) {
        SingleCompressEngine<byte[]> engine = CompressEngineFactory.buildBytesSingle(bytes, mCompressSpecCreator.create());
        CompressTaskController.getInstance().addTask(mCompressTask.obtainTag(), engine);
        return engine;
    }

    @Override
    public BatchCompressEngine<List<byte[]>, byte[]> load(byte[]... bytes) {
        List<byte[]> list = new ArrayList<>();
        Collections.addAll(list, bytes);
        return load(list);
    }

    /**
     * bitmap
     *
     * @param bitmap
     * @return
     */
    @Override
    public SingleCompressEngine<Bitmap> load(Bitmap bitmap) {
        SingleCompressEngine<Bitmap> engine = CompressEngineFactory.buildBitmapSingle(bitmap, mCompressSpecCreator.create());
        CompressTaskController.getInstance().addTask(mCompressTask.obtainTag(), engine);
        return engine;
    }

    @Override
    public BatchCompressEngine<List<Bitmap>, Bitmap> load(Bitmap... bitmap) {
        List<Bitmap> list = new ArrayList<>();
        Collections.addAll(list, bitmap);
        return load(list);
    }

    /**
     * List
     *
     * @param list
     * @return
     */
    @Override
    public <T extends List<I>, I> BatchCompressEngine<T, I> load(T list) {
        BatchCompressEngine<T, I> engine = null;
        if (list != null && !list.isEmpty()) {
            int index = 0;
            Object object = list.get(index);
            while (object == null && index < list.size()) {
                index++;
                object = list.get(index);
            }
            if (object instanceof File) {
                engine = (BatchCompressEngine<T, I>) CompressEngineFactory.buildFileBatch((List<File>) list, mCompressSpecCreator.create());
            } else if (object instanceof FileDescriptor) {
                engine = (BatchCompressEngine<T, I>) CompressEngineFactory.buildFileDescriptorBatch((List<FileDescriptor>) list, mCompressSpecCreator.create());
            } else if (object instanceof String) {
                engine = (BatchCompressEngine<T, I>) CompressEngineFactory.buildFilePathBatch((List<String>) list, mCompressSpecCreator.create());
            } else if (object instanceof Uri) {

            } else if (object instanceof Integer) {
                engine = (BatchCompressEngine<T, I>) CompressEngineFactory.buildResourceBatch((List<Integer>) list, mCompressSpecCreator.create());
            } else if (object instanceof InputStream) {
                List<InputStream> inputStreamList = new ArrayList<>();
                for (I is : list) {
                    ByteArrayOutputStream baos = null;
                    InputStream inputStream;
                    try {
                        baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = ((InputStream) is).read(buffer)) > -1) {
                            baos.write(buffer, 0, len);
                        }
                        baos.flush();
                        inputStream = new ByteArrayInputStream(baos.toByteArray());
                        inputStreamList.add(inputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            ((InputStream) is).close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            is = null;
                        }
                        try {
                            baos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            baos = null;
                        }
                    }
                }
                engine = (BatchCompressEngine<T, I>) CompressEngineFactory.buildInputStreamBatch(inputStreamList, mCompressSpecCreator.create());
            } else if (object instanceof byte[]) {
                engine = (BatchCompressEngine<T, I>) CompressEngineFactory.buildBytesBatch((List<byte[]>) list, mCompressSpecCreator.create());
            } else if (object instanceof Bitmap) {
                engine = (BatchCompressEngine<T, I>) CompressEngineFactory.buildBitmapBatch((List<Bitmap>) list, mCompressSpecCreator.create());
            }
        }
        CompressTaskController.getInstance().addTask(mCompressTask.obtainTag(), engine);
        return engine;
    }

    @Override
    public CompressTaskBuilder compressSpec(CompressSpec compressSpec) {
        mCompressSpecCreator.compressSpec(compressSpec);
        return this;
    }

    @Override
    public CompressTaskBuilder calculation(Calculation calculation) {
        mCompressSpecCreator.calculation(calculation);
        return this;
    }

    @Override
    public CompressTaskBuilder addDecoration(Decoration decoration) {
        mCompressSpecCreator.addDecoration(decoration);
        return this;
    }

    @Override
    public CompressTaskBuilder options(FileCompressOptions compressOptions) {
        mCompressSpecCreator.options(compressOptions);
        return this;
    }

    @Override
    public CompressTaskBuilder bitmapConfig(Bitmap.Config config) {
        mCompressSpecCreator.bitmapConfig(config);
        return this;
    }

    @Override
    public CompressTaskBuilder maxFileSize(float maxSize) {
        mCompressSpecCreator.maxFileSize(maxSize);
        return this;
    }

    @Override
    public CompressTaskBuilder compressTaskNum(int n) {
        mCompressSpecCreator.compressTaskNum(n);
        return this;
    }

    @Override
    public CompressTaskBuilder safeMemory(int safeMemory) {
        mCompressSpecCreator.safeMemory(safeMemory);
        return this;
    }

    @Override
    public CompressTaskBuilder diskDirectory(File rootDirectory) {
        mCompressSpecCreator.diskDirectory(rootDirectory);
        return this;
    }

}
