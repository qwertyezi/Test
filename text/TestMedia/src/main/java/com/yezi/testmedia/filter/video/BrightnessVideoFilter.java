package com.yezi.testmedia.filter.video;

import android.opengl.GLES20;

import com.yezi.testmedia.R;

/**
 * brightness value ranges from -1.0 to 1.0, with 0.0 as the normal level
 */
public class BrightnessVideoFilter extends VideoFilter {

    private int glBrightness;
    private float mBrightness = 0.0f;

    public BrightnessVideoFilter() {
        super(0, R.raw.brightness_video_fragment);
    }

    public BrightnessVideoFilter setBrightness(float brightness) {
        mBrightness = Math.min(1.0f, Math.max(-1.0f, brightness));
        return this;
    }

    @Override
    public void onDraw() {
        super.onDraw();
        GLES20.glUniform1f(glBrightness, mBrightness);
    }

    @Override
    public void onCreated(int mProgram) {
        super.onCreated(mProgram);
        glBrightness = GLES20.glGetUniformLocation(mProgram, "uBrightness");
    }

    @Override
    public void onChanged(int width, int height) {
        super.onChanged(width, height);
    }
}
