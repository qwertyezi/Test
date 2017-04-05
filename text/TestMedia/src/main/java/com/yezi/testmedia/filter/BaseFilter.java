package com.yezi.testmedia.filter;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.yezi.testmedia.R;
import com.yezi.testmedia.utils.ScaleType;
import com.yezi.testmedia.utils.ShaderUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public abstract class BaseFilter implements GLSurfaceView.Renderer {

    public static final int NO_FILTER = -1;

    private int mProgram;
    private int glPosition;
    private int glTexture;
    private int glCoordinate;
    private int glMatrix;

    private FloatBuffer mPos;
    private FloatBuffer mCoord;

    protected int mDataWidth = 0;
    protected int mDataHeight = 0;
    protected int mViewWidth;
    protected int mViewHeight;

    private int mTextureId = NO_FILTER;
    private int mVertex;
    private int mFragment;
    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];
    private ScaleType mScaleType = ScaleType.CENTER_INSIDE;

    private static final float[] sPos = {
            -1.0f, 1.0f,
            -1.0f, -1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f
    };

    private static final float[] sCoord = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
    };

    public BaseFilter() {
        this(0, 0);
    }

    public BaseFilter(int vertexRes, int fragmentRes) {
        mVertex = vertexRes == 0 ? R.raw.default_vertex : vertexRes;
        mFragment = fragmentRes == 0 ? R.raw.default_fragment : fragmentRes;

        initBuffer();
    }

    private void initBuffer() {
        mPos = ByteBuffer
                .allocateDirect(sPos.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(sPos);
        mPos.position(0);

        mCoord = ByteBuffer
                .allocateDirect(sCoord.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(sCoord);
        mCoord.position(0);
    }

    public void setTextureId(int textureId) {
        mTextureId = textureId;
    }

    public int getTextureId() {
        return mTextureId;
    }

    public float[] getMVPMatrix() {
        return mMVPMatrix;
    }

    public void setMVPMatrix(float[] MVPMatrix) {
        mMVPMatrix = MVPMatrix;
    }

    public int getGlMatrix() {
        return glMatrix;
    }

    public void setGlMatrix(int glMatrix) {
        this.glMatrix = glMatrix;
    }

    public void setDataSize(int width, int height) {
        mDataWidth = width;
        mDataHeight = height;
    }

    public void setScaleType(ScaleType type) {
        mScaleType = type;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mProgram = ShaderUtils.createProgram(mVertex, mFragment);

        glPosition = GLES20.glGetAttribLocation(mProgram, "vPosition");
        glCoordinate = GLES20.glGetAttribLocation(mProgram, "vCoordinate");
        glTexture = GLES20.glGetUniformLocation(mProgram, "vTexture");
        glMatrix = GLES20.glGetUniformLocation(mProgram, "vMatrix");

        onCreated(mProgram);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mViewWidth = width;
        mViewHeight = height;

        int w = mDataWidth == 0 ? width : mDataWidth;
        int h = mDataHeight == 0 ? height : mDataHeight;
        float s1 = w / (float) width;
        float s2 = h / (float) height;
        float ratio = height / (float) width;
        switch (mScaleType) {
            case CENTER_CROP:
                if (s1 > s2) {
                    //w剪裁
                    Matrix.orthoM(mProjectMatrix, 0, -h / (float) w / ratio, h / (float) w / ratio, -1, 1, -1, 1);
                } else {
                    //h剪裁
                    Matrix.orthoM(mProjectMatrix, 0, -1, 1, -w / (float) h * ratio, w / (float) h * ratio, -1, 1);
                }
                break;
            case CENTER_INSIDE:
                if (s1 > s2) {
                    //w满屏
                    Matrix.orthoM(mProjectMatrix, 0, -1, 1, -w / (float) h * ratio, w / (float) h * ratio, -1, 1);
                } else {
                    //h满屏
                    Matrix.orthoM(mProjectMatrix, 0, -h / (float) w / ratio, h / (float) w / ratio, -1, 1, -1, 1);
                }
                break;
            case FIT_XY:
                Matrix.orthoM(mProjectMatrix, 0, -1, 1, -1, 1, -1, 1);
                break;
            default:
        }

        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 1.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0);

        onChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glUseProgram(mProgram);

        onDraw();

        GLES20.glUniformMatrix4fv(glMatrix, 1, false, mMVPMatrix, 0);

        GLES20.glEnableVertexAttribArray(glPosition);
        GLES20.glEnableVertexAttribArray(glCoordinate);
        mPos.position(0);
        mCoord.position(0);
        GLES20.glVertexAttribPointer(glPosition, 2, GLES20.GL_FLOAT, false, 0, mPos);
        GLES20.glVertexAttribPointer(glCoordinate, 2, GLES20.GL_FLOAT, false, 0, mCoord);

        if (mTextureId != NO_FILTER) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
            GLES20.glUniform1i(glTexture, 0);
        }

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glDisableVertexAttribArray(glPosition);
        GLES20.glDisableVertexAttribArray(glCoordinate);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    public abstract void onDraw();

    public abstract void onCreated(int mProgram);

    public abstract void onChanged(int width, int height);
}
