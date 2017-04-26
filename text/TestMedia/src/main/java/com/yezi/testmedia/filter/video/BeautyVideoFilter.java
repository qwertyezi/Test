package com.yezi.testmedia.filter.video;

import android.opengl.GLES20;

import com.yezi.testmedia.R;

public class BeautyVideoFilter extends VideoFilter {

    private int glAaCoef;
    private int glMixCoef;
    private int glIternum;

    private float aaCoef;
    private float mixCoef;
    private int iternum;

    private float[] mMVPMatrix = new float[16];

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
    public void onDraw() {
        super.onDraw();

        GLES20.glUniform1f(glAaCoef, aaCoef);
        GLES20.glUniform1f(glMixCoef, mixCoef);
        GLES20.glUniform1i(glIternum, iternum);
    }

    @Override
    public void onCreated(int mProgram) {
        super.onCreated(mProgram);

        glAaCoef = GLES20.glGetUniformLocation(mProgram, "uAaCoef");
        glMixCoef = GLES20.glGetUniformLocation(mProgram, "uMixCoef");
        glIternum = GLES20.glGetUniformLocation(mProgram, "uIternum");

//        if (mVideoType == VideoType.CAMERA) {
//            CameraEngine.setOnCameraSwitchListener(new CameraEngine.onCameraSwitchListener() {
//                @Override
//                public void onCameraSwitch(boolean isFront) {
//                    if (isFront) {
//                        Matrix.multiplyMM(mFlipMatrix, 0, mMVPMatrix, 0, GL2Utils.flip(GL2Utils.getOriginalMatrix(), false, true), 0);
//                        setMVPMatrix(mFlipMatrix);
//                    } else {
//                        setMVPMatrix(mMVPMatrix);
//                    }
//                }
//            });
//        }
    }

    @Override
    public void onChanged(int width, int height) {
//        mMVPMatrix = getMVPMatrix();
        super.onChanged(width, height);
    }
}
