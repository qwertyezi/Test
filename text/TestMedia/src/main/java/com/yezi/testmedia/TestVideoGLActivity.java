package com.yezi.testmedia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yezi.testmedia.filter.video.BrightnessVideoFilter;
import com.yezi.testmedia.filter.video.GrayVideoFilter;
import com.yezi.testmedia.filter.video.VideoFilter;
import com.yezi.testmedia.view.VideoGLSurfaceView;

public class TestVideoGLActivity extends AppCompatActivity {

    private VideoGLSurfaceView mSurfaceView;
    private static final VideoFilter[] filters = {
            new GrayVideoFilter(), new BrightnessVideoFilter().setBrightness(-0.3f)
    };
    private int mCurrentFilter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_videogl);
        mSurfaceView = (VideoGLSurfaceView) findViewById(R.id.surface_view);
    }

    public void onPlayClick(View view) {
        mSurfaceView.setLoopPlay(true);
        mSurfaceView.playVideo("android.resource://" + getPackageName() + "/" + R.raw.test);
//        mSurfaceView.setFilter(new BrightnessVideoFilter());
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
        mSurfaceView.release();
    }
}
