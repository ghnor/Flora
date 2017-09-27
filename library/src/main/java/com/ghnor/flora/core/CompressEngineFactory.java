package com.ghnor.flora.core;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ghnor.flora.common.ApplicationLoader;
import com.ghnor.flora.spec.CompressSpec;

import java.io.File;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by ghnor on 2017/9/6.
 * ghnor.me@gmail.com
 */

public final class CompressEngineFactory {

    private CompressEngineFactory() {
        throw new RuntimeException("can not be a instance");
    }

    public static SingleCompressEngine<String> buildFilePathSingle(String filePath, CompressSpec compressSpec) {
        return new SingleCompressEngine<String>(filePath, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(String filePath, BitmapFactory.Options options) {
                BitmapFactory.decodeFile(filePath, options);
                return options;
            }

            @Override
            protected Callable<String> getCallable(String filePath, CompressSpec spec) {
                return new CompressCallableFactory.FilePathCompressCallable(filePath, spec);
            }
        };
    }

    public static BatchCompressEngine<List<String>, String> buildFilePathBatch(List<String> filePaths, CompressSpec compressSpec) {
        return new BatchCompressEngine<List<String>, String>(filePaths, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(String filePath, BitmapFactory.Options options) {
                BitmapFactory.decodeFile(filePath, options);
                return options;
            }

            @Override
            protected Callable<String> getCallable(String filePath, CompressSpec spec) {
                return new CompressCallableFactory.FilePathCompressCallable(filePath, spec);
            }
        };
    }

    public static SingleCompressEngine<File> buildFileSingle(File file, CompressSpec compressSpec) {
        return new SingleCompressEngine<File>(file, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(File file, BitmapFactory.Options options) {
                BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                return options;
            }

            @Override
            protected Callable<String> getCallable(File file, CompressSpec spec) {
                return new CompressCallableFactory.FileCompressCallable(file, spec);
            }
        };
    }

    public static BatchCompressEngine<List<File>, File> buildFileBatch(List<File> files, CompressSpec compressSpec) {
        return new BatchCompressEngine<List<File>, File>(files, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(File file, BitmapFactory.Options options) {
                BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                return options;
            }

            @Override
            protected Callable<String> getCallable(File file, CompressSpec spec) {
                return new CompressCallableFactory.FileCompressCallable(file, spec);
            }
        };
    }

    public static SingleCompressEngine<FileDescriptor> buildFileDescriptorSingle(FileDescriptor fileDescriptor, CompressSpec compressSpec) {
        return new SingleCompressEngine<FileDescriptor>(fileDescriptor, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(FileDescriptor fileDescriptor, BitmapFactory.Options options) {
                BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                return options;
            }

            @Override
            protected Callable<String> getCallable(FileDescriptor fileDescriptor, CompressSpec spec) {
                return new CompressCallableFactory.FileDescriptorCompressCallable(fileDescriptor, spec);
            }
        };
    }

    public static BatchCompressEngine<List<FileDescriptor>, FileDescriptor> buildFileDescriptorBatch(List<FileDescriptor> fileDescriptors, CompressSpec compressSpec) {
        return new BatchCompressEngine<List<FileDescriptor>, FileDescriptor>(fileDescriptors, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(FileDescriptor fileDescriptor, BitmapFactory.Options options) {
                BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                return options;
            }

            @Override
            protected Callable<String> getCallable(FileDescriptor fileDescriptor, CompressSpec spec) {
                return null;
            }
        };
    }

    public static SingleCompressEngine<Integer> buildResourceSingle(Integer integer, CompressSpec compressSpec) {
        return new SingleCompressEngine<Integer>(integer, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(Integer integer, BitmapFactory.Options options) {
                Resources resources = ApplicationLoader.getApplication().getResources();
                BitmapFactory.decodeResource(resources, integer, options);
                return options;
            }

            @Override
            protected Callable<String> getCallable(Integer integer, CompressSpec spec) {
                return new CompressCallableFactory.ResourceCompressCallable(integer, spec);
            }
        };
    }

    public static BatchCompressEngine<List<Integer>, Integer> buildResourceBatch(List<Integer> integers, CompressSpec compressSpec) {
        return new BatchCompressEngine<List<Integer>, Integer>(integers, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(Integer integer, BitmapFactory.Options options) {
                Resources resources = ApplicationLoader.getApplication().getResources();
                BitmapFactory.decodeResource(resources, integer, options);
                return options;
            }

            @Override
            protected Callable<String> getCallable(Integer integer, CompressSpec spec) {
                return new CompressCallableFactory.ResourceCompressCallable(integer, spec);
            }
        };
    }

    public static SingleCompressEngine<InputStream> buildInputStreamSingle(InputStream inputStream, CompressSpec compressSpec) {
        return new SingleCompressEngine<InputStream>(inputStream, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(InputStream inputStream, BitmapFactory.Options options) throws Exception {
                BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.reset();
                return options;
            }

            @Override
            protected Callable<String> getCallable(InputStream inputStream, CompressSpec spec) {
                return new CompressCallableFactory.InputStreamCompressCallable(inputStream, spec);
            }
        };
    }

    public static BatchCompressEngine<List<InputStream>, InputStream> buildInputStreamBatch(List<InputStream> inputStreams, CompressSpec compressSpec) {
        return new BatchCompressEngine<List<InputStream>, InputStream>(inputStreams, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(InputStream inputStream, BitmapFactory.Options options) throws Exception {
                BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.reset();
                return options;
            }

            @Override
            protected Callable<String> getCallable(InputStream inputStream, CompressSpec spec) {
                return new CompressCallableFactory.InputStreamCompressCallable(inputStream, spec);
            }
        };
    }

    public static SingleCompressEngine<byte[]> buildBytesSingle(byte[] bytes, CompressSpec compressSpec) {
        return new SingleCompressEngine<byte[]>(bytes, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(byte[] bytes, BitmapFactory.Options options) {
                BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                return options;
            }

            @Override
            protected Callable<String> getCallable(byte[] bytes, CompressSpec spec) {
                return new CompressCallableFactory.BytesCompressCallable(bytes, spec);
            }
        };
    }

    public static BatchCompressEngine<List<byte[]>, byte[]> buildBytesBatch(List<byte[]> bytes, CompressSpec compressSpec) {
        return new BatchCompressEngine<List<byte[]>, byte[]>(bytes, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(byte[] bytes, BitmapFactory.Options options) {
                BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                return options;
            }

            @Override
            protected Callable<String> getCallable(byte[] bytes, CompressSpec spec) {
                return new CompressCallableFactory.BytesCompressCallable(bytes, spec);
            }
        };
    }

    public static SingleCompressEngine<Bitmap> buildBitmapSingle(Bitmap bitmap, CompressSpec compressSpec) {
        return new SingleCompressEngine<Bitmap>(bitmap, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(Bitmap bitmap, BitmapFactory.Options options) {
                options.outWidth = bitmap.getWidth();
                options.outHeight = bitmap.getHeight();
                options.inPreferredConfig = bitmap.getConfig();
                return options;
            }

            @Override
            protected Callable<String> getCallable(Bitmap bitmap, CompressSpec spec) {
                return new CompressCallableFactory.BitmapCompressCallable(bitmap, spec);
            }
        };
    }

    public static BatchCompressEngine<List<Bitmap>, Bitmap> buildBitmapBatch(List<Bitmap> bitmaps, CompressSpec compressSpec) {
        return new BatchCompressEngine<List<Bitmap>, Bitmap>(bitmaps, compressSpec) {
            @Override
            protected BitmapFactory.Options getDecodeOptions(Bitmap bitmap, BitmapFactory.Options options) {
                options.outWidth = bitmap.getWidth();
                options.outHeight = bitmap.getHeight();
                options.inPreferredConfig = bitmap.getConfig();
                return options;
            }

            @Override
            protected Callable<String> getCallable(Bitmap bitmap, CompressSpec spec) {
                return new CompressCallableFactory.BitmapCompressCallable(bitmap, spec);
            }
        };
    }

}
