package com.yezi.testmedia.filter;

import android.opengl.GLES20;

import com.yezi.testmedia.R;

public class GrayFilter extends BaseFilter {

    private int glChangeColor;

    public GrayFilter() {
        super(0, R.raw.gray_fragment);
    }

    @Override
    public void onDraw() {
        GLES20.glUniform3fv(glChangeColor, 1, new float[]{0.299f, 0.587f, 0.114f}, 0);
    }

    @Override
    public void onCreated(int mProgram) {
        glChangeColor = GLES20.glGetUniformLocation(mProgram, "uChangeColor");
    }

    @Override
    public void onChanged(int width, int height) {

    }
}
