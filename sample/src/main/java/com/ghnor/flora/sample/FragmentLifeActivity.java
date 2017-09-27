package com.ghnor.flora.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ghnor on 2017/9/23.
 * ghnor.me@gmail.com
 */

public class FragmentLifeActivity extends AppCompatActivity {

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, FragmentLifeActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        getSupportActionBar().setTitle("测试Fragment的生命周期跟随");
        getFragmentManager().beginTransaction().replace(R.id.container, LifeFragment.newInstance(), LifeFragment.class.getSimpleName()).commitAllowingStateLoss();
    }
}
