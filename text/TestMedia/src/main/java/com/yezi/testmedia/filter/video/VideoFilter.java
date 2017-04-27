package com.yezi.testmedia.filter.video;

import android.opengl.GLES20;

import com.yezi.testmedia.R;
import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.utils.enums.FilterType;
import com.yezi.testmedia.utils.enums.VideoType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.yezi.testmedia.utils.GL2Utils.FRAGMENT_POSITION_VIDEO;

public class VideoFilter extends BaseFilter {

    protected int glSTMatrix;
    protected float[] mTransformMatrix = new float[16];
    protected VideoType mVideoType = VideoType.VIDEO;

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
        mCoord = ByteBuffer
                .allocateDirect(FRAGMENT_POSITION_VIDEO.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(FRAGMENT_POSITION_VIDEO);
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
