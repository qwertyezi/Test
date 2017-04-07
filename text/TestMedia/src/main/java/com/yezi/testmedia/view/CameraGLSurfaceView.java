package com.yezi.testmedia.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.yezi.testmedia.render.VideoRender;
import com.yezi.testmedia.utils.VideoType;
import com.yezi.testmedia.utils.camera.CameraEngine;

public class CameraGLSurfaceView extends GLSurfaceView implements SurfaceTexture.OnFrameAvailableListener {

    private VideoRender mVideoRender;

    public CameraGLSurfaceView(Context context) {
        this(context, null);
    }

    public CameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mVideoRender = new VideoRender(VideoType.CAMERA);
        setEGLContextClientVersion(2);
        setRenderer(mVideoRender);
        setRenderMode(RENDERMODE_WHEN_DIRTY);

        mVideoRender.setOnSurfaceCreatedListener(new VideoRender.onSurfaceCreatedListener() {
            @Override
            public void onSurfaceCreated() {
                mVideoRender.getSurfaceTexture().setOnFrameAvailableListener(CameraGLSurfaceView.this);
                CameraEngine.openCamera();
                CameraEngine.startPreview(mVideoRender.getSurfaceTexture());
            }
        });
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }
}
