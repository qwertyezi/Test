package com.yezi.testmedia.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.yezi.testmedia.filter.BaseFilter;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class BaseRender implements GLSurfaceView.Renderer {

    protected BaseFilter mFilter;

    public BaseRender() {
        mFilter = new BaseFilter(0, 0) {

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
        mFilter = filter;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
//        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
//        GLES20.glDisable(GLES20.GL_STENCIL_TEST);

        mFilter.setTextureId(createTexture());
        mFilter.onSurfaceCreated(gl, config);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        mFilter.onSurfaceChanged(gl, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mFilter.onDrawFrame(gl);
    }

    public int createTexture() {
        return BaseFilter.NO_FILTER;
    }
}
