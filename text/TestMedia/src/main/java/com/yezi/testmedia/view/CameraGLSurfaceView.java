package com.yezi.testmedia.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.yezi.testmedia.filter.video.VideoFilter;
import com.yezi.testmedia.render.VideoRecordRender;
import com.yezi.testmedia.render.VideoRender;
import com.yezi.testmedia.utils.camera.CameraEngine;
import com.yezi.testmedia.utils.enums.VideoType;

public class CameraGLSurfaceView extends GLSurfaceView implements SurfaceTexture.OnFrameAvailableListener {

    private VideoRecordRender mVideoRender;

    public CameraGLSurfaceView(Context context) {
        this(context, null);
    }

    public CameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public void setFilter(final VideoFilter filter) {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mVideoRender.setFilter(filter);
                requestRender();
            }
        });
    }

    public void startRecording() {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                mVideoRender.startRecording(EGL14.eglGetCurrentContext());
            }
        });
    }

    public void stopRecording() {
        mVideoRender.stopRecording();
    }

    private void init() {
        mVideoRender = new VideoRecordRender(VideoType.CAMERA);
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
