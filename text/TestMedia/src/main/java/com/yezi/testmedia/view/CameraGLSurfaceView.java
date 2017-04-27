package com.yezi.testmedia.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.yezi.testmedia.filter.video.VideoFilter;
import com.yezi.testmedia.render.VideoRecordRender;
import com.yezi.testmedia.render.VideoRender;
import com.yezi.testmedia.utils.BitmapUtils;
import com.yezi.testmedia.utils.GL2Utils;
import com.yezi.testmedia.utils.camera.CameraInstance;
import com.yezi.testmedia.utils.enums.ScaleType;
import com.yezi.testmedia.utils.enums.VideoType;

import java.nio.IntBuffer;

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

    @Override
    public void onResume() {
        super.onResume();
        CameraInstance.getInstance().resumeCamera();
    }

    public void onDestroy() {
        mVideoRender.stopRecording();
        CameraInstance.getInstance().releaseCamera();
    }

    public void stopRecording() {
        mVideoRender.stopRecording();
    }

    public void setScaleType(ScaleType scaleType) {
        mVideoRender.setScaleType(scaleType);
    }

    public ScaleType getScaleType() {
        return mVideoRender.getScaleType();
    }

    private void init() {
        mVideoRender = new VideoRecordRender(VideoType.CAMERA);
        mVideoRender.setScaleType(ScaleType.FIT_XY);
        setEGLContextClientVersion(2);
        setRenderer(mVideoRender);
        setRenderMode(RENDERMODE_WHEN_DIRTY);

        mVideoRender.setOnSurfaceCreatedListener(new VideoRender.onSurfaceCreatedListener() {
            @Override
            public void onSurfaceCreated() {
                mVideoRender.getSurfaceTexture().setOnFrameAvailableListener(CameraGLSurfaceView.this);
                CameraInstance.getInstance().setCameraSize(600, 800,false);
                CameraInstance.getInstance().openCamera();
                mVideoRender.setDataSize(CameraInstance.getInstance().getPreviewSize().height, CameraInstance.getInstance().getPreviewSize().width);
                CameraInstance.getInstance().startPreview(mVideoRender.getSurfaceTexture());
            }
        });
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
                BitmapUtils.saveBitmap(bitmap, filePath);
            }
        });
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }
}
