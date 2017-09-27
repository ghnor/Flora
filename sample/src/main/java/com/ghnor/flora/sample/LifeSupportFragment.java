package com.ghnor.flora.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * Created by ghnor on 2017/9/23.
 * ghnor.me@gmail.com
 */

public class LifeSupportFragment extends Fragment {
    protected List<ImageInfoBean> mSourceImageList = new ArrayList<>();
    protected RecyclerView mSourceImageRecyclerView;
    protected RecyclerViewAdapter mSourceImageAdapter;

    protected List<ImageInfoBean> mResultImageList = new ArrayList<>();
    protected RecyclerView mResultImageRecyclerView;
    protected RecyclerViewAdapter mResultImageAdapter;

    public static LifeSupportFragment newInstance() {
        return new LifeSupportFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSourceImageRecyclerView = (RecyclerView) view.findViewById(R.id.rv_source);
        mSourceImageAdapter = new RecyclerViewAdapter(mSourceImageList);
        mSourceImageRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mSourceImageRecyclerView.setAdapter(mSourceImageAdapter);

        mResultImageRecyclerView = (RecyclerView) view.findViewById(R.id.rv_result);
        mResultImageAdapter = new RecyclerViewAdapter(mResultImageList);
        mResultImageRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mResultImageRecyclerView.setAdapter(mResultImageAdapter);

        view.findViewById(R.id.select_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxGalleryFinal.with(getActivity())
                        .image()
                        .multiple()
                        .maxSize(8)
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

                                compressByUs(pathList);
                            }
                        }).openGallery();
            }
        });
    }

    private void compressByUs(List<String> path) {
        Flora.with(this).load(path).compress(new Callback<List<String>>() {
            @Override
            public void callback(List<String> compressResults) {
                setupResultInfo(compressResults);
            }
        });
    }

    protected void setupResultInfo(List<String> pathList) {
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
}
