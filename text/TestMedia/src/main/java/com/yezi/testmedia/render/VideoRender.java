package com.yezi.testmedia.render;

import android.graphics.SurfaceTexture;

import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.filter.NoFilter;
import com.yezi.testmedia.utils.enums.FilterType;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VideoRender extends BaseRender {

    protected SurfaceTexture mSurfaceTexture;
    protected float[] mTransformMatrix = new float[16];

    public VideoRender() {
        super(new NoFilter().setFilterType(FilterType.VIDEO));
    }

    public VideoRender(BaseFilter filter) {
        super(filter);
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

        mFilter.setTextureId(mTextureId);
        mSurfaceTexture = new SurfaceTexture(mTextureId);

        if (mListener != null) {
            mListener.onSurfaceCreated();
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mSurfaceTexture.updateTexImage();

        mSurfaceTexture.getTransformMatrix(mTransformMatrix);
        mFilter.setTransformMatrix(mTransformMatrix);

        super.onDrawFrame(gl);
    }

    public SurfaceTexture getSurfaceTexture() {
        return mSurfaceTexture;
    }

    @Override
    public void release() {
        super.release();
        if (mSurfaceTexture != null) {
            mSurfaceTexture.release();
            mSurfaceTexture = null;
        }
    }

}
