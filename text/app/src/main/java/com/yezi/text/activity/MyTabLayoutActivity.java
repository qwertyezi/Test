package com.yezi.text.activity;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yezi.text.R;
import com.yezi.text.adapter.MyViewpagerAdapter;
import com.yezi.text.widget.mytablayout.MyTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyTabLayoutActivity extends AppCompatActivity {

    @Bind(R.id.tablayout)
    MyTabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tablayout);
        ButterKnife.bind(this);

        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new MyViewpagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
