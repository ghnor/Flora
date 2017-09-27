package com.ghnor.flora.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghnor on 2017/9/16.
 * ghnor.me@gmail.com
 */

public class BaseActivity extends AppCompatActivity {
    protected List<ImageInfoBean> mSourceImageList = new ArrayList<>();
    protected RecyclerView mSourceImageRecyclerView;
    protected RecyclerViewAdapter mSourceImageAdapter;

    protected List<ImageInfoBean> mResultImageList = new ArrayList<>();
    protected RecyclerView mResultImageRecyclerView;
    protected RecyclerViewAdapter mResultImageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        mSourceImageRecyclerView = (RecyclerView) findViewById(R.id.rv_source);
        mSourceImageAdapter = new RecyclerViewAdapter(mSourceImageList);
        mSourceImageRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mSourceImageRecyclerView.setAdapter(mSourceImageAdapter);

        mResultImageRecyclerView = (RecyclerView) findViewById(R.id.rv_result);
        mResultImageAdapter = new RecyclerViewAdapter(mResultImageList);
        mResultImageRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mResultImageRecyclerView.setAdapter(mResultImageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_single:
                SingleActivity.openActivity(this);
                break;
            case R.id.action_batch:
                BatchActivity.openActivity(this);
                break;
            case R.id.action_test_fragment:
                FragmentLifeActivity.openActivity(this);
                break;
            case R.id.action_test_support_fragment:
                SupportFragmentLifeActivity.openActivity(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void recycle() {
        mSourceImageList.clear();
        mSourceImageAdapter.notifyDataSetChanged();
        mResultImageList.clear();
        mResultImageAdapter.notifyDataSetChanged();
    }
}
