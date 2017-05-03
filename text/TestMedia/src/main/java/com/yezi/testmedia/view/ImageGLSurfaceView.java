package com.yezi.testmedia.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.render.ImageRender;
import com.yezi.testmedia.utils.BitmapUtils;
import com.yezi.testmedia.utils.GL2Utils;
import com.yezi.testmedia.utils.enums.ScaleType;

import java.nio.IntBuffer;

public class ImageGLSurfaceView extends GLSurfaceView {

    private ImageRender mImageRender;
    private BaseFilter mFilter;

    public ImageGLSurfaceView(Context context) {
        this(context, null);
    }

    public ImageGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private interface ResultBitmapCallback {
        void resultBitmap(Bitmap bitmap);
    }

    private synchronized void getResultBitmap(final ResultBitmapCallback callback) {
        if (callback == null) {
            return;
        }
        queueEvent(new Runnable() {
            @Override
            public void run() {
                int imageWidth = getMeasuredWidth();
                int imageHeight = getMeasuredHeight();
                IntBuffer intBuffer = IntBuffer.allocate(imageWidth * imageHeight);
                GLES20.glReadPixels(0, 0, imageWidth, imageHeight, GLES20.GL_RGBA,
                        GLES20.GL_UNSIGNED_BYTE, intBuffer);
                Bitmap bitmap = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888);
                bitmap.copyPixelsFromBuffer(GL2Utils.convertMirroredImage(intBuffer, imageWidth, imageHeight));
                intBuffer.clear();
                callback.resultBitmap(bitmap);
            }
        });
    }

    public synchronized void saveBitmap(final String filePath) {
        getResultBitmap(new ResultBitmapCallback() {
            @Override
            public void resultBitmap(Bitmap bitmap) {
                BitmapUtils.saveBitmap(bitmap, filePath);
            }
        });
    }

    public void setBitmap(final Bitmap bitmap) {
        mImageRender.setBitmap(bitmap);
        requestRender();
    }

    public void setFilter(BaseFilter filter) {
        mFilter = filter;
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mImageRender.setFilter(mFilter);
                requestRender();
            }
        });
    }

    public void setScaleType(ScaleType scaleType) {
        mImageRender.setScaleType(scaleType);
        requestRender();
    }

    public ScaleType getScaleType() {
        return mImageRender.getScaleType();
    }

    private void init() {
        mImageRender = new ImageRender();
        setEGLContextClientVersion(2);
        setRenderer(mImageRender);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void release() {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mImageRender.release();
            }
        });
    }
}
