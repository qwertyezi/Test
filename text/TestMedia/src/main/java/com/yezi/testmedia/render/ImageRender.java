package com.yezi.testmedia.render;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.yezi.testmedia.filter.BaseFilter;

import javax.microedition.khronos.opengles.GL10;

public class ImageRender extends BaseRender {

    private Bitmap mBitmap;
    private boolean mRecycleBitmap;

    public ImageRender() {
        super();
    }

    public ImageRender(BaseFilter filter) {
        mFilter = filter;
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
    public void onDrawFrame(GL10 gl) {
        mFilter.setTextureId(createTexture(mFilter.getTextureId()));

        super.onDrawFrame(gl);
    }

    @Override
    public int createTexture(int textureId) {
        int[] texture = new int[1];
        if (mBitmap != null) {
            if (textureId == BaseFilter.NO_FILTER) {
                GLES20.glGenTextures(1, texture, 0);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
            } else {
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
                texture[0] = textureId;
            }
            if (mRecycleBitmap) {
                mBitmap.recycle();
            }
            return texture[0];
        }
        return textureId;
    }

}
