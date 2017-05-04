package com.yezi.testmedia.filter;

import android.opengl.GLES20;

import com.yezi.testmedia.R;
import com.yezi.testmedia.utils.enums.FilterType;

import java.nio.FloatBuffer;

public class BeautyFilter2 extends BaseFilter {

    private int mWidth, mHeight;
    private float[] mLocation;

    private int mSingleStepOffsetLocation;
    private int mParamsLocation;

    public BeautyFilter2() {
        this(FilterType.IMAGE);
    }

    public BeautyFilter2(FilterType filterType) {
        super(0, R.raw.beauty_fragment2);
        setFilterType(filterType);
    }

    public BeautyFilter2 setBeautyLevel(int level) {
        switch (level) {
            case 1:
                mLocation = new float[]{1.0f, 1.0f, 0.15f, 0.15f};
                break;
            case 2:
                mLocation = new float[]{0.8f, 0.9f, 0.2f, 0.2f};
                break;
            case 3:
                mLocation = new float[]{0.6f, 0.8f, 0.25f, 0.25f};
                break;
            case 4:
                mLocation = new float[]{0.4f, 0.7f, 0.38f, 0.3f};
                break;
            case 5:
                mLocation = new float[]{0.33f, 0.63f, 0.4f, 0.35f};
                break;
            default:
                break;
        }
        return this;
    }

    @Override
    public void onDraw() {
        GLES20.glUniform2fv(mSingleStepOffsetLocation, 1, FloatBuffer.wrap(new float[]{2.0f / mWidth, 2.0f / mHeight}));
        GLES20.glUniform4fv(mParamsLocation, 1, FloatBuffer.wrap(mLocation));
    }

    @Override
    public void onCreated(int mProgram) {
        mSingleStepOffsetLocation = GLES20.glGetUniformLocation(mProgram, "singleStepOffset");
        mParamsLocation = GLES20.glGetUniformLocation(mProgram, "params");
    }

    @Override
    public void onChanged(int width, int height) {
        mWidth = width;
        mHeight = height;
    }
}
