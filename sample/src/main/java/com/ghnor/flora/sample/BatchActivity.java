package com.ghnor.flora.sample;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ghnor on 2017/9/17.
 * ghnor.me@gmail.com
 */

public class BatchActivity extends BaseActivity {
    private static final String TAG = BatchActivity.class.getSimpleName();

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, BatchActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Batch");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_file:
                recycle();
                testFile();
                break;
            case R.id.action_file_path:
                recycle();
                testFile();
                break;
            case R.id.action_res:
                recycle();
                testResource();
                break;
            case R.id.action_is:
                recycle();
                testStream();
                break;
            case R.id.action_bytes:
                recycle();
                testBytes();
                break;
            case R.id.action_bitmap:
                recycle();
                testBitmap();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSourceInfo(Bitmap[] bitmaps, long[] sizes) {
        if (bitmaps == null || sizes == null) {
            return;
        }
        for (int index = 0; index < bitmaps.length; index++) {
            ImageInfoBean bean = new ImageInfoBean();
            bean.img = bitmaps[index];
            bean.size = sizes[index];
            bean.width = bitmaps[index].getWidth();
            bean.height = bitmaps[index].getHeight();
            mSourceImageList.add(bean);
        }
        mSourceImageAdapter.notifyDataSetChanged();
    }

    private void setupResultInfo(List<String> pathList) {
        mResultImageList.clear();
        for (String path : pathList) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            ImageInfoBean bean = new ImageInfoBean();
            bean.path = path;
            bean.size = new File(path).length();
            bean.width = bitmap.getWidth();
            bean.height = bitmap.getHeight();
            mResultImageList.add(bean);
        }
        mResultImageAdapter.notifyDataSetChanged();
    }

    private void testFile() {
        try {
            Bitmap[] bitmaps = new Bitmap[4];
            long[] sizes = new long[4];
            File[] files = new File[4];
            for (int index = 0; index < 4; index++) {
                int n = index + 1;
                final InputStream is = getResources().getAssets().open("test" + n + ".jpg");
                long fileSize = is.available();
                final File outfile = new File(getExternalFilesDir(null), "batch-test-" + n + ".jpg");
                FileOutputStream fos = new FileOutputStream(outfile);
                byte[] buffer = new byte[4096];
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.close();

                Bitmap originBitmap = BitmapFactory.decodeFile(outfile.getAbsolutePath());

                bitmaps[index] = originBitmap;
                sizes[index] = fileSize;
                files[index] = outfile;

                is.close();
            }

            setupSourceInfo(bitmaps, sizes);

            Flora.with().load(files).compress(new Callback<List<String>>() {
                @Override
                public void callback(List<String> strings) {
                    setupResultInfo(strings);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testBitmap() {
        try {
            Bitmap[] bitmaps = new Bitmap[4];
            long[] sizes = new long[4];
            for (int index = 0; index < 4; index++) {
                int n = index + 1;
                final InputStream is = getResources().getAssets().open("test" + n + ".jpg");
                long fileSize = is.available();
                Bitmap originBitmap = BitmapFactory.decodeStream(is);

                bitmaps[index] = originBitmap;
                sizes[index] = fileSize;

                is.close();
            }

            setupSourceInfo(bitmaps, sizes);

            Flora.with().load(bitmaps).compress(new Callback<List<String>>() {
                @Override
                public void callback(List<String> strings) {
                    setupResultInfo(strings);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testResource() {
        try {
            int[] resIds = new int[]{R.drawable.test1, R.drawable.test2, R.drawable.test3, R.drawable.test4};
            Resources resources = getApplication().getResources();
            Bitmap[] bitmaps = new Bitmap[4];
            long[] sizes = new long[4];
            for (int index = 0; index < 4; index++) {
                InputStream is = null;
                is = resources.openRawResource(resIds[index], new TypedValue());
                sizes[index] = is.available();
                bitmaps[index] = BitmapFactory.decodeStream(is);
                is.close();
            }
            setupSourceInfo(bitmaps, sizes);

            Flora.with().load(resIds).compress(new Callback<List<String>>() {
                @Override
                public void callback(List<String> strings) {
                    setupResultInfo(strings);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testStream() {
        try {
            Bitmap[] bitmaps = new Bitmap[4];
            InputStream[] inputStreams = new InputStream[4];
            long[] sizes = new long[4];
            for (int index = 0; index < 4; index++) {
                int i = index + 1;
                final InputStream is = getResources().getAssets().open("test" + i + ".jpg");
                File outfile = new File(getExternalFilesDir(null), "test-" + i + ".jpg");
                FileOutputStream fos = new FileOutputStream(outfile);
                byte[] buffer = new byte[4096];
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                InputStream inputStream = new FileInputStream(outfile);
                Bitmap originBitmap = BitmapFactory.decodeStream(is);
                bitmaps[index] = originBitmap;
                inputStreams[index] = inputStream;
                sizes[index] = outfile.length();
            }
            setupSourceInfo(bitmaps, sizes);

            Flora.with().load(inputStreams).compress(new Callback<List<String>>() {
                @Override
                public void callback(List<String> strings) {
                    setupResultInfo(strings);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testBytes() {
        try {
            Bitmap[] bitmaps = new Bitmap[4];
            long[] sizes = new long[4];
            byte[][] bytes = new byte[4][];
            for (int index = 0; index < 4; index++) {
                int n = index + 1;
                final InputStream is = getResources().getAssets().open("test" + n + ".jpg");

                long fileSize = is.available();

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.close();
                is.close();
                byte[] bitmapBytes = os.toByteArray();

                Bitmap originBitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);

                bitmaps[index] = originBitmap;
                sizes[index] = fileSize;
                bytes[index] = bitmapBytes;
            }


            setupSourceInfo(bitmaps, sizes);

            Flora.with().load(bytes).compress(new Callback<List<String>>() {
                @Override
                public void callback(List<String> strings) {
                    setupResultInfo(strings);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
