package com.yezi.testmedia.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;

import com.yezi.testmedia.BuildConfig;
import com.yezi.testmedia.render.VideoRender;
import com.yezi.testmedia.utils.enums.ScaleType;

import java.io.IOException;

public class VideoGLSurfaceView extends BaseGLSurfaceView implements SurfaceTexture.OnFrameAvailableListener {

    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "VideoGLSurfaceView";

    private Uri mUri;
    private MediaPlayer mMediaPlayer;
    private boolean mLoopPlay = false;
    private float mCurrentVolume;
    private boolean mHasVolume = true;

    public VideoGLSurfaceView(Context context) {
        super(context);
    }

    public VideoGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        mBaseRender = new VideoRender();
        mBaseRender.setScaleType(ScaleType.CENTER_CROP);
        setRenderer(mBaseRender);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        getCurrentVolume();

        ((VideoRender) mBaseRender).setOnSurfaceCreatedListener(new VideoRender.onSurfaceCreatedListener() {
            @Override
            public void onSurfaceCreated() {
                if (mMediaPlayer == null) {
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setLooping(mLoopPlay);
                    ((VideoRender) mBaseRender).getSurfaceTexture().setOnFrameAvailableListener(VideoGLSurfaceView.this);
                    mMediaPlayer.setSurface(new Surface(((VideoRender) mBaseRender).getSurfaceTexture()));
                    try {
                        mMediaPlayer.setDataSource(getContext(), mUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mMediaPlayer.prepareAsync();
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mBaseRender.setDataSize(mp.getVideoWidth(), mp.getVideoHeight());

                            mp.start();
                        }
                    });
                }
            }
        });
    }

    public void setLoopPlay(boolean loopPlay) {
        mLoopPlay = loopPlay;
    }

    public void setVideoUri(String uri) {
        if (TextUtils.isEmpty(uri)) {
            return;
        }
        mUri = Uri.parse(uri);
    }

    private void getCurrentVolume() {
        AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mCurrentVolume = volume / (float) maxVolume;
    }

    public void setVolume(float volume) {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.setVolume(volume, volume);
    }

    public void toggleVolume() {
        mHasVolume = !mHasVolume;
        setVolume(mHasVolume ? mCurrentVolume : 0.0f);
    }

    @Override
    public void release() {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                if (mMediaPlayer != null) {

                    mMediaPlayer.setSurface(null);
                    if (mMediaPlayer.isPlaying())
                        mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }

                mBaseRender.release();
            }
        });
    }

    private int mFrameCount = 0;
    private long mLastTime = 0;

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();

        if (DEBUG) {
            if (mLastTime == 0) {
                mLastTime = System.currentTimeMillis();
            }
            ++mFrameCount;
            if (System.currentTimeMillis() - mLastTime >= 1000) {
                Log.i(TAG, "视频帧率：" + mFrameCount);
                mFrameCount = 0;
                mLastTime = 0;
            }
        }
    }
}
