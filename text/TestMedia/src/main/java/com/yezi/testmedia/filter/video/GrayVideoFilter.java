package com.yezi.testmedia.filter.video;

import android.opengl.GLES20;

import com.yezi.testmedia.R;

public class GrayVideoFilter extends VideoFilter {

    private int glChangeColor;

    public GrayVideoFilter() {
        super(0, R.raw.gray_video_fragment);
    }

    @Override
    public void onDraw() {
        super.onDraw();
        GLES20.glUniform3fv(glChangeColor, 1, new float[]{0.299f, 0.587f, 0.114f}, 0);
    }

    @Override
    public void onCreated(int mProgram) {
        super.onCreated(mProgram);
        glChangeColor = GLES20.glGetUniformLocation(mProgram, "uChangeColor");
    }

    @Override
    public void onChanged(int width, int height) {
        super.onChanged(width, height);
    }
}
