package com.yezi.testmedia.filter.video;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.yezi.testmedia.R;
import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.utils.enums.FilterType;
import com.yezi.testmedia.utils.GL2Utils;
import com.yezi.testmedia.utils.enums.VideoType;
import com.yezi.testmedia.utils.camera.CameraEngine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VideoFilter extends BaseFilter {

    private int glSTMatrix;
    private float[] mTransformMatrix = new float[16];
    private VideoType mVideoType = VideoType.VIDEO;
    private float[] mMvpMatrix;

    public void setTransformMatrix(float[] matrix) {
        mTransformMatrix = matrix;
    }

    public VideoFilter() {
        this(0, 0);
    }

    public VideoFilter(int vertexRes, int fragmentRes) {
        super(vertexRes == 0 ? R.raw.default_video_vertex : vertexRes,
                fragmentRes == 0 ? R.raw.default_video_fragment : fragmentRes);
        setFilterType(FilterType.VIDEO);
    }

    public void setVideoType(VideoType videoType) {
        mVideoType = videoType;
    }

    @Override
    public void initTextureBuffer() {
        float[] position = mVideoType == VideoType.VIDEO ? GL2Utils.FRAGMENT_POSITION_180 : GL2Utils.FRAGMENT_POSITION_90;
        mCoord = ByteBuffer
                .allocateDirect(position.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(position);
        mCoord.position(0);
    }

    @Override
    public void onDraw() {
        if (mVideoType == VideoType.CAMERA) {
            if (CameraEngine.isFrontCamera()) {
                float[] flipMatrix = new float[16];
                Matrix.multiplyMM(flipMatrix, 0, mMvpMatrix, 0, GL2Utils.flip(GL2Utils.getOriginalMatrix(), false, true), 0);
                setMVPMatrix(flipMatrix);
            } else {
                setMVPMatrix(mMvpMatrix);
            }
        }

        GLES20.glUniformMatrix4fv(glSTMatrix, 1, false, mTransformMatrix, 0);
    }

    @Override
    public void onCreated(int mProgram) {
        glSTMatrix = GLES20.glGetUniformLocation(mProgram, "uSTMatrix");
    }

    @Override
    public void onChanged(int width, int height) {
        mMvpMatrix = getMVPMatrix();
    }
}
