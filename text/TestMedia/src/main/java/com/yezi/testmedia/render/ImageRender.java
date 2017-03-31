package com.yezi.testmedia.render;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.yezi.testmedia.filter.BaseFilter;

public class ImageRender extends BaseRender {

    private Bitmap mBitmap;
    private boolean mRecycleBitmap;

    public ImageRender() {
        super();
    }

    public ImageRender(BaseFilter filter) {
        mFilter = filter;
    }

    @Override
    public void setFilter(BaseFilter filter) {
        super.setFilter(filter);
        if (mBitmap != null) {
            mFilter.setDataSize(mBitmap.getWidth(), mBitmap.getHeight());
        }
    }

    public void setBitmap(Bitmap bitmap) {
        setBitmap(bitmap, false);
    }

    public void setBitmap(Bitmap bitmap, boolean recycleBitmap) {
        mBitmap = bitmap;
        mRecycleBitmap = recycleBitmap;
        mFilter.setDataSize(mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    public int createTexture() {
        int[] texture = new int[1];
        if (mBitmap != null) {
            GLES20.glGenTextures(1, texture, 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
            if (mRecycleBitmap) {
                mBitmap.recycle();
            }
            return texture[0];
        }
        return BaseFilter.NO_FILTER;
    }

}
