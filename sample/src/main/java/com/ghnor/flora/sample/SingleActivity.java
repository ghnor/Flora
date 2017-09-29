package com.ghnor.flora.sample;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SingleActivity extends BaseActivity {

    private static final String TAG = SingleActivity.class.getSimpleName();

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, SingleActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Single");
    }

    @Override
    protected void onDestroy() {
        Flora.cancel(TAG);
        super.onDestroy();
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

    private void setupSourceInfo(Bitmap bitmap, long sizeBytes) {
        ImageInfoBean bean = new ImageInfoBean();
        bean.img = bitmap;
        bean.size = sizeBytes;
        bean.width = bitmap.getWidth();
        bean.height = bitmap.getHeight();
        mSourceImageList.add(bean);
        mSourceImageAdapter.notifyDataSetChanged();
    }

    private void setupResultInfo(String outfile, long sizeBytes) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(outfile, options);
        ImageInfoBean bean = new ImageInfoBean();
        bean.path = outfile;
        bean.size = new File(outfile).length();
        bean.width = options.outWidth;
        bean.height = options.outHeight;
        mResultImageList.add(bean);
        mResultImageAdapter.notifyDataSetChanged();
    }

    private void testFile() {
        try {
            final InputStream is = getResources().getAssets().open("test1.jpg");
            File outfile = new File(getExternalFilesDir(null), "test-1.jpg");
            FileOutputStream fos = new FileOutputStream(outfile);
            byte[] buffer = new byte[4096];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            is.close();

            Bitmap originBitmap = BitmapFactory.decodeFile(outfile.getAbsolutePath());

            setupSourceInfo(originBitmap, outfile.length());

            Flora.with(TAG).load(outfile).compress(new Callback<String>() {
                @Override
                public void callback(String s) {
                    File file = new File(s);
                    setupResultInfo(s, file.length());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testResource() {
        try {
            Resources resources = getApplication().getResources();
            Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test2);
            final InputStream is = getResources().getAssets().open("test2.jpg");
            setupSourceInfo(originBitmap, is.available());
            is.close();

            Flora.with(TAG).load(R.drawable.test2).compress(new Callback<String>() {
                @Override
                public void callback(String s) {
                    File file = new File(s);
                    setupResultInfo(s, file.length());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testStream() {
        try {
            final InputStream is = getResources().getAssets().open("test3.jpg");

            File outfile = new File(getExternalFilesDir(null), "test-3.jpg");
            FileOutputStream fos = new FileOutputStream(outfile);
            byte[] buffer = new byte[4096];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();

            InputStream is2 = new FileInputStream(outfile);

            Bitmap originBitmap = BitmapFactory.decodeStream(is);

            setupSourceInfo(originBitmap, outfile.length());

            Flora.with(TAG).load(is2).compress(new Callback<String>() {
                @Override
                public void callback(String s) {
                    File file = new File(s);
                    setupResultInfo(s, file.length());
                }
            });
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testBytes() {
        try {
            final InputStream is = getResources().getAssets().open("test4.jpg");

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

            setupSourceInfo(originBitmap, fileSize);

            Flora.with(TAG).load(bitmapBytes).compress(new Callback<String>() {
                @Override
                public void callback(String s) {
                    File file = new File(s);
                    setupResultInfo(s, file.length());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testBitmap() {
        try {
            final InputStream is = getResources().getAssets().open("test5.jpg");

            long fileSize = is.available();
            Bitmap originBitmap = BitmapFactory.decodeStream(is);

            setupSourceInfo(originBitmap, fileSize);

            Flora.with(TAG).load(originBitmap).compress(new Callback<String>() {
                @Override
                public void callback(String s) {
                    File file = new File(s);
                    setupResultInfo(s, file.length());
                }
            });
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
