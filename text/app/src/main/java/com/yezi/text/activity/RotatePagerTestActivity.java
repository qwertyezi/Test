package com.yezi.text.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yezi.text.R;
import com.yezi.text.widget.RotatePager.RotatePager;
import com.yezi.text.widget.RotatePager.RotatePagerAdapter;

import java.util.Arrays;

/**
 * Created by yezi on 16-11-4.
 */

public class RotatePagerTestActivity extends AppCompatActivity {

    private static final String[] sDatas = new String[]{
            "http://oc1om4k3y.bkt.clouddn.com/image_201609210933473050", "http://oc1om4k3y.bkt.clouddn.com/image_201609210934033242",
            "http://oc1om4k3y.bkt.clouddn.com/image_201609210934209496", "http://oc1om4k3y.bkt.clouddn.com/image_201609210931147400",
            "http://oc1om4k3y.bkt.clouddn.com/image_201609210934209496"
    };
    private RotatePager mRotatePager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rotatepager_test);

        mRotatePager = (RotatePager) findViewById(R.id.rotate_pager);
        RotatePagerAdapter adapter = new RotatePagerAdapter(this);
        mRotatePager.setAdapter(adapter);

        adapter.updateData(Arrays.asList(sDatas));
    }
}
