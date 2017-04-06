package com.yezi.testmedia.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Surface;

import com.yezi.testmedia.filter.BaseFilter;
import com.yezi.testmedia.render.VideoRender;

import java.io.IOException;

public class VideoGLSurfaceView extends GLSurfaceView implements SurfaceTexture.OnFrameAvailableListener {

    private VideoRender mVideoRender;
    private Uri mUri;
    private MediaPlayer mMediaPlayer;
    private boolean mLoopPlay = false;

    public VideoGLSurfaceView(Context context) {
        this(context, null);
    }

    public VideoGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mVideoRender = new VideoRender();
        setEGLContextClientVersion(2);
        setRenderer(mVideoRender);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    public void setLoopPlay(boolean loopPlay) {
        mLoopPlay = loopPlay;
    }

    public void playVideo(String uri) {
        playVideo(uri, null);
    }

    public void playVideo(String uri, final BaseFilter filter) {
        if (TextUtils.isEmpty(uri)) {
            return;
        }
        mUri = Uri.parse(uri);
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setLooping(mLoopPlay);
            mVideoRender.getSurfaceTexture().setOnFrameAvailableListener(this);
            mMediaPlayer.setSurface(new Surface(mVideoRender.getSurfaceTexture()));
            try {
                mMediaPlayer.setDataSource(getContext(), mUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (filter != null) {
                        mVideoRender.setFilter(filter);
                    }
                    mVideoRender.setDataSize(mp.getVideoWidth(), mp.getVideoHeight());

                    mp.start();
                }
            });
        }
    }

    public void release() {
        queueEvent(new Runnable() {
            @Override
            public void run() {
                if(mMediaPlayer != null) {

                    mMediaPlayer.setSurface(null);
                    if(mMediaPlayer.isPlaying())
                        mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }

                mVideoRender.release();
            }
        });
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }
}
