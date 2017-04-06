package com.yezi.testmedia.filter;

import android.opengl.GLES20;

import com.yezi.testmedia.R;
import com.yezi.testmedia.utils.FilterType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VideoFilter extends BaseFilter {

    private int glSTMatrix;
    private float[] mTransformMatrix = new float[16];

    private static final float[] sCoord = {
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            0.0f, 0.0f,
    };

    public void setTransformMatrix(float[] matrix) {
        mTransformMatrix = matrix;
    }

    public VideoFilter() {
        super(R.raw.default_video_vertex, R.raw.default_video_fragment);
        setFilterType(FilterType.VIDEO);
    }

    @Override
    public void initTextureBuffer() {
        mCoord = ByteBuffer
                .allocateDirect(sCoord.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(sCoord);
        mCoord.position(0);
    }

    @Override
    public void onDraw() {
        GLES20.glUniformMatrix4fv(glSTMatrix, 1, false, mTransformMatrix, 0);
    }

    @Override
    public void onCreated(int mProgram) {
        glSTMatrix = GLES20.glGetUniformLocation(mProgram, "uSTMatrix");
    }

    @Override
    public void onChanged(int width, int height) {

    }
}
