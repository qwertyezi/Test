package com.yezi.testmedia.filter;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.yezi.testmedia.utils.GL2Utils;
import com.yezi.testmedia.utils.enums.FilterType;
import com.yezi.testmedia.utils.enums.ScaleType;

public class BlurFilter extends BaseFilter {

    private int[] mTextureDownScale;
    private FrameBufferObject mFramebuffer;
    private static final int LEVEL = 30;
    private int mIntensity = 0;
    private float[] mFlipMatrix = new float[16];
    private static final int BLUR_SIZE = 512;

    public BlurFilter() {
        this(FilterType.IMAGE);
    }

    public BlurFilter(FilterType filterType) {
        super();
        setFilterType(filterType);
    }

    public BlurFilter setIntensity(int intensity) {
        if (intensity != mIntensity && intensity >= 0) {
            mIntensity = intensity;
            if (mIntensity > LEVEL)
                mIntensity = LEVEL;
        }
        return this;
    }

    private int calcMips(int len, int level) {
        return len / (level + 1);
    }

    @Override
    public void onCreated(int mProgram) {
        mTextureDownScale = new int[LEVEL];
        GLES20.glGenTextures(LEVEL, mTextureDownScale, 0);

        for (int i = 0; i < LEVEL; ++i) {
            GLES20.glBindTexture(TEXTURE_2D_BINDABLE, mTextureDownScale[i]);
            GLES20.glTexImage2D(TEXTURE_2D_BINDABLE, 0, GLES20.GL_RGBA, calcMips(BLUR_SIZE, i + 1), calcMips(BLUR_SIZE, i + 1), 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
            GLES20.glTexParameteri(TEXTURE_2D_BINDABLE, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(TEXTURE_2D_BINDABLE, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(TEXTURE_2D_BINDABLE, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(TEXTURE_2D_BINDABLE, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        }

        mFramebuffer = new FrameBufferObject();
    }

    @Override
    public void onDrawFrame() {
        if (mIntensity == 0) {
            super.onDrawFrame();
            return;
        }

        setScaleType(ScaleType.FIT_XY);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        mFramebuffer.bindTexture(mTextureDownScale[0]);

        GLES20.glViewport(0, 0, calcMips(BLUR_SIZE, 1), calcMips(BLUR_SIZE, 1));
        super.onDrawFrame();

        for (int i = 1; i < mIntensity; ++i) {
            mFramebuffer.bindTexture(mTextureDownScale[i]);
            setTextureId(mTextureDownScale[i - 1]);
            GLES20.glViewport(0, 0, calcMips(BLUR_SIZE, i + 1), calcMips(BLUR_SIZE, i + 1));
            super.onDrawFrame();
        }

        for (int i = mIntensity - 1; i > 0; --i) {
            mFramebuffer.bindTexture(mTextureDownScale[i - 1]);
            setTextureId(mTextureDownScale[i]);
            GLES20.glViewport(0, 0, calcMips(BLUR_SIZE, i), calcMips(BLUR_SIZE, i));
            super.onDrawFrame();
        }

        GLES20.glViewport(0, 0, mViewWidth, mViewHeight);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        setTextureId(mTextureDownScale[0]);
        setScaleType(ScaleType.CENTER_INSIDE);
        Matrix.multiplyMM(mFlipMatrix, 0, getMVPMatrix(), 0, GL2Utils.flip(GL2Utils.getOriginalMatrix(), false, true), 0);
        setMVPMatrix(mFlipMatrix);
        super.onDrawFrame();
    }

    @Override
    public void onDraw() {

    }

    @Override
    public void onChanged(int width, int height) {

    }

    private class FrameBufferObject {
        private int mFramebufferID;

        FrameBufferObject() {
            int[] buf = new int[1];
            GLES20.glGenFramebuffers(1, buf, 0);
            mFramebufferID = buf[0];
        }

        public void release() {
            GLES20.glDeleteFramebuffers(1, new int[]{mFramebufferID}, 0);
        }

        void bind() {
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFramebufferID);
        }

        public void bindTexture(int texID) {
            bind();
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, TEXTURE_2D_BINDABLE, texID, 0);
        }

    }
}
