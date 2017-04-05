package com.yezi.testmedia;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.yezi.testmedia.filter.BrightnessFilter;
import com.yezi.testmedia.filter.FilterGroup;
import com.yezi.testmedia.filter.GrayFilter;
import com.yezi.testmedia.view.ImageGLSurfaceView;

public class TestImageGLActivity extends AppCompatActivity {

    private ImageGLSurfaceView mSurfaceView;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_imagegl);

        mSurfaceView = (ImageGLSurfaceView) findViewById(R.id.glsurfaceview);
//        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);

        mSurfaceView.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.girl));
        mSurfaceView.setFilter(new FilterGroup(new BrightnessFilter().setBrightness(-0.3f), new GrayFilter()));

//        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSurfaceView.onPause();
    }
}
