package com.yezi.testmedia.render;

import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.filter.video.VideoFilter;
import com.yezi.testmedia.utils.GL2Utils;
import com.yezi.testmedia.utils.enums.VideoType;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VideoRender extends BaseRender {

    private VideoType mVideoType = VideoType.VIDEO;
    private SurfaceTexture mSurfaceTexture;
    private int[] mTextures = new int[1];

    public VideoRender() {
        this(new VideoFilter());
    }

    public VideoRender(VideoType type) {
        this(new VideoFilter(), type);
    }

    public VideoRender(VideoFilter filter) {
        this(filter, VideoType.VIDEO);
    }

    public VideoRender(VideoFilter filter, VideoType type) {
        filter.setVideoType(type);
        mFilter = filter;
        mVideoType = type;
    }

    public void setDataSize(int width, int height) {
        mFilter.setDataSize(width, height);
    }

    @Override
    public void setFilter(BaseFilter filter) {
        ((VideoFilter) filter).setVideoType(mVideoType);
        super.setFilter(filter);
    }

    @Override
    public int createTexture(int textureId) {
        if (textureId == BaseFilter.NO_FILTER) {
            GLES20.glGenTextures(1, mTextures, 0);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTextures[0]);

            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            return mTextures[0];
        }
        return textureId;
    }

    public interface onSurfaceCreatedListener {
        void onSurfaceCreated();
    }

    private onSurfaceCreatedListener mListener;

    public void setOnSurfaceCreatedListener(onSurfaceCreatedListener listener) {
        mListener = listener;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);

        mFilter.setTextureId(createTexture(mFilter.getTextureId()));
        mSurfaceTexture = new SurfaceTexture(mTextures[0]);

        if (mVideoType == VideoType.CAMERA && mListener != null) {
            mListener.onSurfaceCreated();
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mSurfaceTexture.updateTexImage();

        if (mVideoType == VideoType.VIDEO) {
            float[] mTransformMatrix = new float[16];
            mSurfaceTexture.getTransformMatrix(mTransformMatrix);
            ((VideoFilter) mFilter).setTransformMatrix(mTransformMatrix);
        } else if (mVideoType == VideoType.CAMERA) {
            ((VideoFilter) mFilter).setTransformMatrix(GL2Utils.getOriginalMatrix());
        }

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
