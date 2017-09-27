package com.ghnor.flora.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.FileBitmapDecoderFactory;

import java.util.List;

/**
 * Created by ghnor on 2017/9/16.
 * ghnor.me@gmail.com
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<ImageInfoBean> mList;

    public RecyclerViewAdapter(List<ImageInfoBean> list) {
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent.getContext());
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageInfoBean bean = mList.get(position);
        int width = ((WindowManager) holder.itemView.getContext()
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth() / 3;
        int bitmapWidth = bean.width;
        int bitmapHeight = bean.height;
        float ratio = (float) bitmapWidth / (float) bitmapHeight;
        int height = (int) ((int) ((float) width) / ratio);
        holder.mLargeImageView.getLayoutParams().width = width;
        holder.mLargeImageView.getLayoutParams().height = height;
        if (bean.img != null) {
            holder.mLargeImageView.setImage(bean.img);
        } else {
            holder.mLargeImageView.setImage(new FileBitmapDecoderFactory(bean.path));
        }
        holder.mTextView.setText("宽：" + bean.width + "\r\n" +
                "高：" + bean.height + "\r\n" +
                "大小：" + bean.size/1024);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        LargeImageView mLargeImageView;
        TextView mTextView;

        public MyViewHolder(Context context) {
            this(LayoutInflater.from(context).inflate(R.layout.item, null));
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            mLargeImageView = (LargeImageView) itemView.findViewById(R.id.image_view);
            mTextView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}
