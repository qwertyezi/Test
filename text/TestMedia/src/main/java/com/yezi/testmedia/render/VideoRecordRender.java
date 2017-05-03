package com.yezi.testmedia.render;

import android.opengl.EGLContext;
import android.util.Log;

import com.yezi.testmedia.BuildConfig;
import com.yezi.testmedia.recorder.MediaAudioEncoder;
import com.yezi.testmedia.recorder.MediaEncoder;
import com.yezi.testmedia.recorder.MediaMuxerWrapper;
import com.yezi.testmedia.recorder.MediaVideoEncoder;
import com.yezi.testmedia.recorder.RenderHandler;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

public class VideoRecordRender extends VideoRender {

    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "VideoRecordRender";

    private MediaMuxerWrapper mMuxer;
    private RenderHandler mRenderHandler;
    private boolean mIsRecording = false;

    public VideoRecordRender() {
        super();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);

        if (mIsRecording && mRenderHandler != null) {
            mRenderHandler.doRender(mTransformMatrix);
            mMuxer.frameAvailableSoon();
        }
    }

    public void startRecording(EGLContext eglContext) {
        if (mIsRecording) {
            return;
        }
        if (DEBUG) Log.v(TAG, "startRecording:");
        try {
            mMuxer = new MediaMuxerWrapper(null);
            MediaVideoEncoder videoEncoder = new MediaVideoEncoder(mMuxer, mMediaEncoderListener, 720, 1280);
            new MediaAudioEncoder(mMuxer, mMediaEncoderListener);
            mMuxer.prepare();
            mRenderHandler = new RenderHandler(mFilter,
                    videoEncoder.getInputSurface().setupEGL(eglContext));
            mRenderHandler.startRender();
            mMuxer.startRecording();
            mIsRecording = true;
        } catch (final IOException e) {
            Log.e(TAG, "startCapture:", e);
        }
    }

    public void stopRecording() {
        if (!mIsRecording) {
            return;
        }
        if (DEBUG) Log.v(TAG, "stopRecording:mMuxer=" + mMuxer);
        if (mMuxer != null) {
            mMuxer.stopRecording();
            mMuxer = null;
        }
        if (mRenderHandler != null) {
            mRenderHandler.stopRender();
            mRenderHandler = null;
        }
        mIsRecording = false;
    }

    private final MediaEncoder.MediaEncoderListener mMediaEncoderListener = new MediaEncoder.MediaEncoderListener() {
        @Override
        public void onPrepared(final MediaEncoder encoder) {
            if (DEBUG) Log.v(TAG, "onPrepared:encoder=" + encoder);
        }

        @Override
        public void onStopped(final MediaEncoder encoder) {
            if (DEBUG) Log.v(TAG, "onStopped:encoder=" + encoder);
        }
    };
}
