package com.yezi.testmedia.recorder;

import com.yezi.testmedia.filter.video.VideoFilter;
import com.yezi.testmedia.utils.enums.VideoType;

public class RenderHandler {

    private CodecInputSurface mCodecInputSurface;
    private Thread mThread;
    private VideoFilter mVideoFilter;
    private boolean mCanRender = false;
    private int mTexId;
    private VideoType mVideoType;
    private int mViewWidth, mViewHeight;
    private float[] mTransformMatrix = new float[16];

    public RenderHandler(CodecInputSurface inputSurface, int texId, VideoType videoType, int viewWidth, int viewHeight) {
        mCodecInputSurface = inputSurface;
        mTexId = texId;
        mVideoType = videoType;
        mViewWidth = viewWidth;
        mViewHeight = viewHeight;
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

                mVideoFilter = new VideoFilter();
                mVideoFilter.setTextureId(mTexId);
                mVideoFilter.setVideoType(mVideoType);
                mVideoFilter.onSurfaceCreated();
                mVideoFilter.onSurfaceChanged(mViewWidth, mViewHeight);

                while (true) {
                    if (mCanRender) {
                        mVideoFilter.setTransformMatrix(mTransformMatrix);
                        mVideoFilter.onDrawFrame();
                        mCodecInputSurface.setPresentationTime(getPTSUs());
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

    protected long getPTSUs() {
        return System.nanoTime() / 1000L;
    }
}
