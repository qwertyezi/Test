package com.yezi.testmedia.filter;

import android.opengl.GLES20;

import com.yezi.testmedia.R;

/**
 * brightness value ranges from -1.0 to 1.0, with 0.0 as the normal level
 */
public class BrightnessFilter extends BaseFilter {

    private int glBrightness;
    private float mBrightness = 0.0f;

    public BrightnessFilter() {
        super(0, R.raw.brightness_fragment);
    }

    public BrightnessFilter setBrightness(float brightness) {
        mBrightness = Math.min(1.0f, Math.max(-1.0f, brightness));
        return this;
    }

    @Override
    public void onDraw() {
        GLES20.glUniform1f(glBrightness, mBrightness);
    }

    @Override
    public void onCreated(int mProgram) {
        glBrightness = GLES20.glGetUniformLocation(mProgram, "brightness");
    }

    @Override
    public void onChanged(int width, int height) {

    }
}
