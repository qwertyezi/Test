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
        super(filter);
    }

    public void setBitmap(Bitmap bitmap) {
        setBitmap(bitmap, false);
    }

    public void setBitmap(Bitmap bitmap, boolean recycleBitmap) {
        mBitmap = bitmap;
        mRecycleBitmap = recycleBitmap;

        setDataSize(mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        texImage();
        mFilter.setTextureId(mTextureId);

        super.onDrawFrame(gl);
    }

    private void texImage() {
        if (mBitmap != null && mTextureId != BaseFilter.NO_FILTER) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);
            if (mRecycleBitmap) {
                mBitmap.recycle();
            }
        }
    }

}
