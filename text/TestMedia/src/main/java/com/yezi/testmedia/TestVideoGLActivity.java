package com.yezi.testmedia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yezi.testmedia.filter.video.GrayVideoFilter;
import com.yezi.testmedia.view.VideoGLSurfaceView;

public class TestVideoGLActivity extends AppCompatActivity {

    private VideoGLSurfaceView mSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_videogl);
        mSurfaceView = (VideoGLSurfaceView) findViewById(R.id.surface_view);
    }

    public void onPlayClick(View view) {
        mSurfaceView.setLoopPlay(true);
        mSurfaceView.playVideo("android.resource://" + getPackageName() + "/" + R.raw.test, new GrayVideoFilter());
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
