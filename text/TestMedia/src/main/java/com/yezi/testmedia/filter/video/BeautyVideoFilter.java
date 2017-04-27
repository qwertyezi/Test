package com.yezi.testmedia.filter.video;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.yezi.testmedia.R;
import com.yezi.testmedia.utils.GL2Utils;
import com.yezi.testmedia.utils.camera.CameraInstance;
import com.yezi.testmedia.utils.enums.VideoType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BeautyVideoFilter extends VideoFilter {

    private int glAaCoef;
    private int glMixCoef;
    private int glIternum;

    private float aaCoef;
    private float mixCoef;
    private int iternum;

    private float[] mOriginalMVPMatrix = new float[16];
    private float[] mMatrix = new float[16];

    public BeautyVideoFilter() {
        super(R.raw.beauty_vertex, R.raw.beauty_fragment);
    }

    public BeautyVideoFilter setFlag(int flag) {
        switch (flag) {
            case 1:
                a(1, 0.19f, 0.54f);
                break;
            case 2:
                a(2, 0.29f, 0.54f);
                break;
            case 3:
                a(3, 0.17f, 0.39f);
                break;
            case 4:
                a(3, 0.25f, 0.54f);
                break;
            case 5:
                a(4, 0.13f, 0.54f);
                break;
            case 6:
                a(4, 0.19f, 0.69f);
                break;
            default:
                a(0, 0f, 0f);
                break;
        }
        return this;
    }

    private void a(int a, float b, float c) {
        this.iternum = a;
        this.aaCoef = b;
        this.mixCoef = c;
    }

    @Override
    public void initTextureBuffer() {
        mCoord = ByteBuffer
                .allocateDirect(GL2Utils.FRAGMENT_POSITION_BEAUTY.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(GL2Utils.FRAGMENT_POSITION_BEAUTY);
        mCoord.position(0);
    }

    @Override
    public void onDraw() {
        super.onDraw();

        GLES20.glUniform1f(glAaCoef, aaCoef);
        GLES20.glUniform1f(glMixCoef, mixCoef);
        GLES20.glUniform1i(glIternum, iternum);

        if (mVideoType == VideoType.CAMERA) {
            if (!CameraInstance.getInstance().isFrontCamera()) {
                Matrix.multiplyMM(mMatrix, 0, mOriginalMVPMatrix, 0, GL2Utils.flip(GL2Utils.getOriginalMatrix(), false, true), 0);
                setMVPMatrix(mMatrix);
            } else {
                setMVPMatrix(mOriginalMVPMatrix);
            }
        }
    }

    @Override
    public void onCreated(int mProgram) {
        super.onCreated(mProgram);

        glAaCoef = GLES20.glGetUniformLocation(mProgram, "uAaCoef");
        glMixCoef = GLES20.glGetUniformLocation(mProgram, "uMixCoef");
        glIternum = GLES20.glGetUniformLocation(mProgram, "uIternum");
    }

    @Override
    public void onChanged(int width, int height) {
        super.onChanged(width, height);
        mOriginalMVPMatrix = getMVPMatrix();
    }
}
