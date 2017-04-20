package com.yezi.testmedia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yezi.testmedia.filter.video.BrightnessVideoFilter;
import com.yezi.testmedia.filter.video.GrayVideoFilter;
import com.yezi.testmedia.filter.video.VideoFilter;
import com.yezi.testmedia.utils.camera.CameraEngine;
import com.yezi.testmedia.view.CameraGLSurfaceView;

public class TestCameraGLActivity extends AppCompatActivity {

    private CameraGLSurfaceView mSurfaceView;

    private static final VideoFilter[] filters = {
            new GrayVideoFilter(), new BrightnessVideoFilter().setBrightness(-0.3f), new VideoFilter()
    };
    private int mCurrentFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_cameragl);

        mSurfaceView = (CameraGLSurfaceView) findViewById(R.id.surface_view);
    }

    public void onSwitchClick(View view) {
        CameraEngine.switchCamera();
    }

    public void onFilterChangeClick(View view) {
        ++mCurrentFilter;
        if (mCurrentFilter == filters.length) {
            mCurrentFilter = 0;
        }
        mSurfaceView.setFilter(filters[mCurrentFilter]);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraEngine.releaseCamera();
    }
}
