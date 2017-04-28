package com.yezi.testmedia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.filter.BeautyFilter;
import com.yezi.testmedia.filter.BlurFilter;
import com.yezi.testmedia.filter.BrightnessFilter;
import com.yezi.testmedia.filter.GrayFilter;
import com.yezi.testmedia.utils.camera.CameraInstance;
import com.yezi.testmedia.utils.enums.FilterType;
import com.yezi.testmedia.utils.enums.ScaleType;
import com.yezi.testmedia.view.CameraGLSurfaceView;

public class TestCameraGLActivity extends AppCompatActivity {

    private CameraGLSurfaceView mSurfaceView;

    private final BaseFilter[] filters = {
            new GrayFilter(FilterType.VIDEO),
            new BrightnessFilter(FilterType.VIDEO).setBrightness(-0.3f),
            new BeautyFilter(FilterType.VIDEO).setFlag(6).setCamera(true),
            new BlurFilter(FilterType.VIDEO).setIntensity(16)
    };
    private final ScaleType[] scaleTypes = {
            ScaleType.CENTER_INSIDE, ScaleType.CENTER_CROP, ScaleType.FIT_XY
    };
    private int mCurrentScaleType;
    private int mCurrentFilter;

    private Button mBtnScaleType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test_cameragl);

        mSurfaceView = (CameraGLSurfaceView) findViewById(R.id.surface_view);
        mBtnScaleType = (Button) findViewById(R.id.btn_scale_type);
        mBtnScaleType.setText(mSurfaceView.getScaleType().toString());

        CameraInstance.getInstance().setRotation(getWindowManager().getDefaultDisplay().getRotation());
    }

    public void onSwitchClick(View view) {
        CameraInstance.getInstance().switchCamera();
    }

    public void onFilterChangeClick(View view) {
        ++mCurrentFilter;
        if (mCurrentFilter == filters.length) {
            mCurrentFilter = 0;
        }
        mSurfaceView.setFilter(filters[mCurrentFilter]);
    }

    public void onStopClick(View view) {
        mSurfaceView.stopRecording();
    }

    public void onEncoderClick(View view) {
        mSurfaceView.startRecording();
    }

    public void onScaleTypeChangeClick(View view) {
        ++mCurrentScaleType;
        if (mCurrentScaleType == scaleTypes.length) {
            mCurrentScaleType = 0;
        }
        mSurfaceView.setScaleType(scaleTypes[mCurrentScaleType]);
        mBtnScaleType.setText(scaleTypes[mCurrentScaleType].toString());
    }

    public void onCameraClick(View view) {
        mSurfaceView.saveBitmap(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSurfaceView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSurfaceView.onDestroy();
    }
}
