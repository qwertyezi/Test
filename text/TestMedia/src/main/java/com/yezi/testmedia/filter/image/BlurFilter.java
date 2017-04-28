package com.yezi.testmedia.filter.image;

import android.opengl.GLES20;

import com.yezi.testmedia.filter.BaseFilter;

public class BlurFilter extends BaseFilter {

    private int[] mTextureDownScale;
    private FrameBufferObject mFramebuffer;
    private final int mLevel = 32;
    private int mIntensity = 0;

    public BlurFilter setIntensity(int intensity) {
        if (intensity != mIntensity && intensity >= 0) {
            mIntensity = intensity;
            if (mIntensity > mLevel)
                mIntensity = mLevel;
        }
        return this;
    }

    private int calcMips(int len, int level) {
        return len / (level + 1);
    }

    @Override
    public void onCreated(int mProgram) {
        mTextureDownScale = new int[mLevel];
        GLES20.glGenTextures(mLevel, mTextureDownScale, 0);

        for (int i = 0; i < mLevel; ++i) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDownScale[i]);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, calcMips(mViewWidth, i + 1), calcMips(mViewHeight, i + 1), 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        }

        mFramebuffer = new FrameBufferObject();
    }

    @Override
    public void onDrawFrame() {
        if (mIntensity == 0) {
            super.onDrawFrame();
            return;
        }

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        mTextureDownScale[0] = getTextureId();

        mFramebuffer.bindTexture(mTextureDownScale[0]);

        super.onDrawFrame();

        for (int i = 1; i < mIntensity; ++i) {
            mFramebuffer.bindTexture(mTextureDownScale[i]);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDownScale[i - 1]);
            GLES20.glViewport(0, 0, calcMips(512, i + 1), calcMips(512, i + 1));
            super.onDrawFrame();
        }

        for (int i = mIntensity - 1; i > 0; --i) {
            mFramebuffer.bindTexture(mTextureDownScale[i - 1]);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDownScale[i]);
            GLES20.glViewport(0, 0, calcMips(512, i), calcMips(512, i));
            super.onDrawFrame();
        }

        GLES20.glViewport(0, 0, mViewWidth, mViewHeight);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDownScale[0]);

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
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, texID, 0);
        }

    }
}
