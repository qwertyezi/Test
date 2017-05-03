package com.yezi.testmedia.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.render.BaseRender;
import com.yezi.testmedia.utils.BitmapUtils;
import com.yezi.testmedia.utils.GL2Utils;
import com.yezi.testmedia.utils.enums.ScaleType;

import java.nio.IntBuffer;

public class BaseGLSurfaceView extends GLSurfaceView {

    protected BaseRender mBaseRender;

    public BaseGLSurfaceView(Context context) {
        this(context, null);
    }

    public BaseGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setEGLContextClientVersion(2);
        init();
    }

    protected void init() {

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

    public void setFilter(final BaseFilter filter) {
        if (filter == null) {
            return;
        }
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mBaseRender.setFilter(filter);
            }
        });
    }

    public void setScaleType(ScaleType scaleType) {
        mBaseRender.setScaleType(scaleType);
    }

    public ScaleType getScaleType() {
        return mBaseRender.getScaleType();
    }

    public void release() {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mBaseRender.release();
            }
        });
    }
}
