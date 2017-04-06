package com.yezi.testmedia.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.render.ImageRender;
import com.yezi.testmedia.utils.GL2Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;

public class ImageGLSurfaceView extends GLSurfaceView {

    private ImageRender mImageRender;
    private BaseFilter mFilter;
    private Context mContext;

    public ImageGLSurfaceView(Context context) {
        this(context, null);
    }

    public ImageGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        init();
    }

    public interface ResultBitmapCallback {
        void resultBitmap(Bitmap bitmap);
    }

    public synchronized void getResultBitmap(final ResultBitmapCallback callback) {
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
                if (bitmap == null) {
                    return;
                }
                File file;
                if (TextUtils.isEmpty(filePath)) {
                    file = new File(mContext.getExternalMediaDirs()[0].getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
                } else {
                    file = new File(filePath);
                }
                try {
                    FileOutputStream fout = new FileOutputStream(file);
                    BufferedOutputStream bos = new BufferedOutputStream(fout);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    bos.flush();
                    bos.close();
                    bitmap.recycle();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setBitmap(Bitmap bitmap) {
        mImageRender.setBitmap(bitmap);
        requestRender();
    }

    public void setFilter(BaseFilter filter) {
        mFilter = filter;
        mImageRender.setFilter(mFilter);
        requestRender();
    }

    private void init() {
        mImageRender = new ImageRender();
        setEGLContextClientVersion(2);
        setRenderer(mImageRender);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void release() {
        mFilter.releaseTexture();
    }
}
