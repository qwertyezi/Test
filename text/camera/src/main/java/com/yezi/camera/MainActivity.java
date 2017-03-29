package com.yezi.camera;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yezi.camera.camera.CameraEngine;

public class MainActivity extends AppCompatActivity {

    private CameraGLSurfaceView mCameraGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCameraGLSurfaceView = (CameraGLSurfaceView) findViewById(R.id.surface_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCameraGLSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraGLSurfaceView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraEngine.releaseCamera();
    }
}
