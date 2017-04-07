package com.yezi.testmedia.render;

import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.filter.video.VideoFilter;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VideoRender extends BaseRender {

    private SurfaceTexture mSurfaceTexture;
    private int[] mTextures = new int[1];

    public VideoRender() {
        this(new VideoFilter());
    }

    public VideoRender(VideoFilter filter) {
        mFilter = filter;
    }

    public void setDataSize(int width, int height) {
        mFilter.setDataSize(width, height);
    }

    @Override
    public int createTexture(int textureId) {
        if (textureId == BaseFilter.NO_FILTER) {
            GLES20.glGenTextures(1, mTextures, 0);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTextures[0]);
            return mTextures[0];
        }
        return textureId;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);

        mFilter.setTextureId(createTexture(mFilter.getTextureId()));
        mSurfaceTexture = new SurfaceTexture(mTextures[0]);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mSurfaceTexture.updateTexImage();
        float[] mTransformMatrix = new float[16];
        mSurfaceTexture.getTransformMatrix(mTransformMatrix);
        ((VideoFilter) mFilter).setTransformMatrix(mTransformMatrix);

        super.onDrawFrame(gl);
    }

    public SurfaceTexture getSurfaceTexture() {
        return mSurfaceTexture;
    }

    public void release() {
        if (mSurfaceTexture != null) {
            mSurfaceTexture.release();
            mSurfaceTexture = null;
        }

        mFilter.release();
    }
}
