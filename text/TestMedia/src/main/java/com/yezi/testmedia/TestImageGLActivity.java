package com.yezi.testmedia;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.filter.image.BrightnessFilter;
import com.yezi.testmedia.filter.image.GrayFilter;
import com.yezi.testmedia.view.ImageGLSurfaceView;

public class TestImageGLActivity extends AppCompatActivity {

    private ImageGLSurfaceView mSurfaceView;
    private SeekBar mSeekBar;
    private static final int[] images = {
            R.mipmap.image_1, R.mipmap.image_2,
            R.mipmap.image_3, R.mipmap.image_4,
            R.mipmap.image_5, R.mipmap.image_6
    };
    private static final BaseFilter[] filters = {
            new GrayFilter(), new BrightnessFilter().setBrightness(-0.3f)
    };
    private int mCurrentImage;
    private int mCurrentFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_imagegl);

        mSurfaceView = (ImageGLSurfaceView) findViewById(R.id.glsurfaceview);
//        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);

        mSurfaceView.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.image_1));
        mCurrentImage = 0;
        mCurrentFilter = 0;

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

    public void onSaveClick(View view) {
        mSurfaceView.saveBitmap(null);
    }

    public void onImageChangeClick(View view) {
        ++mCurrentImage;
        if (mCurrentImage == images.length) {
            mCurrentImage = 0;
        }
        mSurfaceView.setBitmap(BitmapFactory.decodeResource(getResources(), images[mCurrentImage]));
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
