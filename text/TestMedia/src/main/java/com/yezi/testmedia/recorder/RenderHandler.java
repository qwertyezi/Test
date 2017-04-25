package com.yezi.testmedia.recorder;

import com.yezi.testmedia.filter.video.VideoFilter;

public class RenderHandler {

    private CodecInputSurface mCodecInputSurface;
    private Thread mThread;
    private VideoFilter mVideoFilter;
    private boolean mCanRender = false;
    private float[] mTransformMatrix = new float[16];

    public RenderHandler(VideoFilter videoFilter, CodecInputSurface inputSurface) {
        mVideoFilter = videoFilter;
        mCodecInputSurface = inputSurface;
    }

    public void doRender(float[] matrix) {
        mTransformMatrix = matrix;
        mCanRender = true;
    }

    public void startRender() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mCodecInputSurface.makeCurrent();
                while (true) {
                    if (mCanRender) {
                        mVideoFilter.setTransformMatrix(mTransformMatrix);
                        mVideoFilter.onDrawFrame();
                        mCodecInputSurface.swapBuffers();
                        mCanRender = false;
                    }
                }
            }
        });
        mThread.start();
    }

    public void stopRender() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
    }
}
