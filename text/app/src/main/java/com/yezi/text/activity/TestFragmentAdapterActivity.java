package com.yezi.text.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yezi.text.R;
import com.yezi.text.adapter.TestFragmentStateAdapter;

public class TestFragmentAdapterActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_fragment_adapter);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
//        mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
        mAdapter = new TestFragmentStateAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }
}
