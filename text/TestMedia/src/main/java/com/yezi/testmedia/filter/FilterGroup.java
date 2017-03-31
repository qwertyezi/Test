package com.yezi.testmedia.filter;

import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterGroup extends BaseFilter {

    private int[] mFrameBuffers;
    private int[] mTextures;
    private List<BaseFilter> mFilterList = new ArrayList<>();

    public FilterGroup() {

    }

    public FilterGroup(BaseFilter... filters) {
        initFilters(filters);
    }

    public void addFilters(BaseFilter... filters) {
        initFilters(filters);
    }

    public void clearFilter() {
        mFilterList.clear();
    }

    private void initFilters(BaseFilter... filters) {
        Collections.addAll(mFilterList, filters);
        if (mDataWidth != 0 && mDataHeight != 0) {
            initFiltersSize(mDataWidth, mDataHeight);
        }
    }

    @Override
    public void setDataSize(int width, int height) {
        super.setDataSize(width, height);
        initFiltersSize(width, height);
    }

    private void initFiltersSize(int width, int height) {
        for (BaseFilter filter : mFilterList) {
            filter.setDataSize(width, height);
        }
    }

    @Override
    public void onDraw() {
        if (mFrameBuffers == null || mTextures == null) {
            return;
        }
        int previousTexture = getTextureId();
        if (mFilterList != null) {
            int size = mFilterList.size();
            for (int i = 0; i < size; i++) {
                BaseFilter filter = mFilterList.get(i);
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[i]);
                filter.setTextureId(previousTexture);
                filter.onDrawFrame(null);
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
                previousTexture = mTextures[i];
            }
        }
        setTextureId(previousTexture);
    }

    @Override
    public void onCreated(int mProgram) {
        for (BaseFilter filter : mFilterList) {
            filter.onSurfaceCreated(null, null);
        }
        initFrameBuffer();
    }

    private void initFrameBuffer() {
        if (mFilterList != null && mFilterList.size() > 0) {
            int size = mFilterList.size();
            mFrameBuffers = new int[size];
            mTextures = new int[size];

            for (int i = 0; i < size; i++) {
                GLES20.glGenFramebuffers(1, mFrameBuffers, i);
                GLES20.glGenTextures(1, mTextures, i);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[i]);
                GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, mDataWidth, mDataHeight, 0,
                        GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                        GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                        GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                        GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                        GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[i]);
                GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                        GLES20.GL_TEXTURE_2D, mTextures[i], 0);

                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
            }
        }
    }

    @Override
    public void onChanged(int width, int height) {
        for (BaseFilter filter : mFilterList) {
            filter.onSurfaceChanged(null, width, height);
        }
    }
}