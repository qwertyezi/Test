package com.yezi.testmedia.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.utils.enums.ScaleType;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class BaseRender implements GLSurfaceView.Renderer {

    protected BaseFilter mFilter;

    public BaseRender() {
        mFilter = new BaseFilter() {

            @Override
            public void onDraw() {

            }

            @Override
            public void onCreated(int mProgram) {

            }

            @Override
            public void onChanged(int width, int height) {

            }
        };
    }

    public BaseRender(BaseFilter filter) {
        mFilter = filter;
    }

    public void setFilter(BaseFilter filter) {
        filter.setTextureId(mFilter.getTextureId());
        filter.setDataSize(mFilter.getDataWidth(), mFilter.getDataHeight());
        filter.setScaleType(mFilter.getScaleType());
        filter.setFilterType(mFilter.getFilterType());

        filter.onSurfaceCreated();
        filter.onSurfaceChanged(mFilter.getViewWidth(), mFilter.getViewHeight());

        GLES20.glDeleteProgram(mFilter.getProgram());
        mFilter = filter;
    }

    public void setScaleType(ScaleType scaleType) {
        mFilter.setScaleType(scaleType);
    }

    public ScaleType getScaleType() {
        return mFilter.getScaleType();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

        mFilter.onSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        mFilter.onSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mFilter.onDrawFrame();
    }

    public int createTexture(int textureId) {
        return BaseFilter.NO_FILTER;
    }
}
