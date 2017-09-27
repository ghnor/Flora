package com.ghnor.flora.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileBatchCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class MainActivity extends BaseActivity {
    private static final int COMPRESS_LUBAN = 0x01;
    private static final int COMPRESS_TINY = 0x02;
    private static final int COMPRESS_US = 0x03;
    private static final int COMPRESS_NOT = 0x04;
    private RadioGroup mRadioGroup;
    private TextView mUsedTime;
    private int mCompressWay = COMPRESS_LUBAN;
    private long mBeginTime;

    private List<ImageInfoBean> mSourceImageList = new ArrayList<>();
    private RecyclerView mSourceImageRecyclerView;
    private RecyclerViewAdapter mSourceImageAdapter;

    private List<ImageInfoBean> mResultImageList = new ArrayList<>();
    private RecyclerView mResultImageRecyclerView;
    private RecyclerViewAdapter mResultImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.use_luban:
                        mCompressWay = COMPRESS_LUBAN;
                        break;
                    case R.id.use_tiny:
                        mCompressWay = COMPRESS_TINY;
                        break;
                    case R.id.use_us:
                        mCompressWay = COMPRESS_US;
                        break;
                    case R.id.use_not:
                        mCompressWay = COMPRESS_NOT;
                        break;
                }
            }
        });

        findViewById(R.id.select_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxGalleryFinal.with(MainActivity.this)
                        .image()
                        .multiple()
                        .maxSize(9)
                        .imageLoader(ImageLoaderType.GLIDE)
                        .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                                mSourceImageList.clear();
                                mResultImageList.clear();
                                mUsedTime.setText("");

                                List<ImageInfoBean> list = new ArrayList<>();
                                List<String> pathList = new ArrayList<String>();
                                for (MediaBean bean : imageMultipleResultEvent.getResult()) {
                                    ImageInfoBean imageInfoBean = new ImageInfoBean();
                                    imageInfoBean.path = bean.getOriginalPath();
                                    imageInfoBean.width = bean.getWidth();
                                    imageInfoBean.height = bean.getHeight();
                                    imageInfoBean.size = new File(bean.getOriginalPath()).length();
                                    list.add(imageInfoBean);
                                    pathList.add(bean.getOriginalPath());
                                }
                                mSourceImageList.clear();
                                mSourceImageList.addAll(list);
                                mSourceImageAdapter.notifyDataSetChanged();

                                mBeginTime = new Date().getTime();

                                if (mCompressWay == COMPRESS_LUBAN) {
                                    compressByLuban(pathList);
                                } else if (mCompressWay == COMPRESS_TINY) {
                                    compressByTiny(pathList);
                                } else if (mCompressWay == COMPRESS_US) {
                                    compressByUs(pathList);
                                } else if (mCompressWay == COMPRESS_NOT) {

                                }
                            }
                        }).openGallery();
            }
        });

        mUsedTime = (TextView) findViewById(R.id.used_time);


        mSourceImageRecyclerView = (RecyclerView) findViewById(R.id.rv_source);
        mSourceImageAdapter = new RecyclerViewAdapter(mSourceImageList);
        mSourceImageRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mSourceImageRecyclerView.setAdapter(mSourceImageAdapter);

        mResultImageRecyclerView = (RecyclerView) findViewById(R.id.rv_result);
        mResultImageAdapter = new RecyclerViewAdapter(mResultImageList);
        mResultImageRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mResultImageRecyclerView.setAdapter(mResultImageAdapter);
    }

    private void compressByLuban(final List<String> path) {
        final List<String> resultTemp = new ArrayList<>();
        for (String str : path) {
            Luban.get(this).load(new File(str))
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(File file) {
                            resultTemp.add(file.getAbsolutePath());
                            if (resultTemp.size() == path.size()) {
                                toast("压缩完成");
                                setupResultInfo(resultTemp);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    }).launch();
        }
    }

    private void compressByTiny(List<String> path) {
        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
        Tiny.getInstance().debug(true);
        Tiny.getInstance().source(path.toArray(new String[path.size()])).batchAsFile().withOptions(options).batchCompress(new FileBatchCallback() {
            @Override
            public void callback(boolean isSuccess, String[] outfile) {
                toast("压缩完成");
                setupResultInfo(Arrays.asList(outfile));
            }
        });
    }

    private void compressByUs(List<String> path) {
        Flora.with(this).load(path).compress(new Callback<List<String>>() {
            @Override
            public void callback(List<String> compressResults) {
                setupResultInfo(compressResults);
                toast("压缩完成");
            }
        });
    }

    private void setupResultInfo(List<String> pathList) {
        long finishTime = new Date().getTime();
        mUsedTime.setText("耗时：" + dealTime(finishTime - mBeginTime));
        mResultImageList.clear();
        for(String path : pathList) {
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

    private String dealTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:SSSS");
        return dateFormat.format(date);
    }
}
