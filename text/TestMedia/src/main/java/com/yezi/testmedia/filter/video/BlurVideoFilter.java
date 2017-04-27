package com.yezi.testmedia.filter.video;

import android.opengl.GLES20;

import com.yezi.testmedia.R;

public class BlurVideoFilter extends VideoFilter {

    private int texelWidth;
    private int texelHeight;

    public BlurVideoFilter() {
        super(R.raw.blur_video_vertex, R.raw.blur_video_fragment);
    }

    @Override
    public void onCreated(int mProgram) {
        super.onCreated(mProgram);
        texelWidth = GLES20.glGetUniformLocation(mProgram, "texelWidth");
        texelHeight = GLES20.glGetUniformLocation(mProgram, "texelHeight");
    }

    @Override
    public void onDraw() {
        super.onDraw();
        GLES20.glUniform1i(texelWidth, getViewWidth());
        GLES20.glUniform1f(texelHeight, getViewHeight());
    }

    @Override
    public void onChanged(int width, int height) {
        super.onChanged(width, height);
    }
}
