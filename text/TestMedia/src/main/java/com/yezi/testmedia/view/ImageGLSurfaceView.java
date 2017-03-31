package com.yezi.testmedia.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.render.ImageRender;

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

    public void setBitmap(Bitmap bitmap) {
        mImageRender.setBitmap(bitmap);
//        requestRender();
    }

    public void setFilter(BaseFilter filter) {
        mFilter = filter;
        mImageRender.setFilter(mFilter);
//        requestRender();
    }

    private void init() {
        mImageRender = new ImageRender();
        setEGLContextClientVersion(2);
        setRenderer(mImageRender);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
