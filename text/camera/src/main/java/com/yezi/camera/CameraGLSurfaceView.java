package com.yezi.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.yezi.camera.camera.CameraEngine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CameraGLSurfaceView extends GLSurfaceView implements
        GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {

    private static float shapeCoords[] = {
            -1.0f, 1.0f, 0.0f,   // top left
            -1.0f, -1.0f, 0.0f,   // bottom left
            1.0f, -1.0f, 0.0f,   // bottom right
            1.0f, 1.0f, 0.0f}; // top right

    //90 degree rotated
    private static float textureCoords[] = {
            0.0f, 1.0f,   // top left
            1.0f, 1.0f,   // bottom left
            1.0f, 0.0f,    // bottom right
            0.0f, 0.0f}; // top right

    private static short drawOrder[] = {0, 1, 2, 0, 2, 3};

    private static final int COORDS_PER_VERTEX = 3;
    private static final int TEXTURE_COORS_PER_VERTEX = 2;

    private int mProgram;
    private int mTexName = 0;
    private SurfaceTexture mSurfaceTexture;
    private Context mContext;
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTexCoordBuffer;
    private ShortBuffer mDrawListBuffer;
    private int mPositionHandler;
    private int mTexCoordHandler;
    private int mTextureHandler;

    public CameraGLSurfaceView(Context context) {
        this(context, null);
    }

    public CameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private void loadBuffer() {
        mVertexBuffer = ByteBuffer.allocateDirect(4 * shapeCoords.length)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(shapeCoords);
        mVertexBuffer.position(0);

        mTexCoordBuffer = ByteBuffer.allocateDirect(4 * textureCoords.length)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(textureCoords);
        mTexCoordBuffer.position(0);

        mDrawListBuffer = ByteBuffer.allocateDirect(drawOrder.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(drawOrder);
        mDrawListBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        loadBuffer();

        mProgram = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(mContext, R.raw.vertex_shader),
                TextResourceReader.readTextFileFromResource(mContext, R.raw.fragment_shader));

        mPositionHandler = GLES20.glGetAttribLocation(mProgram, "aPosition");
        mTexCoordHandler = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");
        mTextureHandler = GLES20.glGetUniformLocation(mProgram, "sTexture");


        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTexName);

        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        mSurfaceTexture = new SurfaceTexture(mTexName);
        mSurfaceTexture.setOnFrameAvailableListener(this);
        CameraEngine.openCamera();
        CameraEngine.startPreview(mSurfaceTexture);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0, 0, 0, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUseProgram(mProgram);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glEnableVertexAttribArray(mPositionHandler);

        GLES20.glVertexAttribPointer(mPositionHandler, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                COORDS_PER_VERTEX * 4, mVertexBuffer);

        GLES20.glEnableVertexAttribArray(mTexCoordHandler);
        GLES20.glVertexAttribPointer(mTexCoordHandler, TEXTURE_COORS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                TEXTURE_COORS_PER_VERTEX * 4, mTexCoordBuffer);

        GLES20.glUniform1i(mTextureHandler, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, mDrawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandler);
        GLES20.glDisableVertexAttribArray(mTexCoordHandler);

        mSurfaceTexture.updateTexImage();
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }

}
