package com.yezi.testmedia.filter;

import android.opengl.GLES20;

import com.yezi.testmedia.R;
import com.yezi.testmedia.utils.enums.FilterType;

public class BeautyFilter extends BaseFilter {

    private int glAaCoef;
    private int glMixCoef;
    private int glIternum;

    private float aaCoef;
    private float mixCoef;
    private int iternum;

    public BeautyFilter() {
        this(FilterType.IMAGE);
    }

    public BeautyFilter(FilterType filterType) {
        super(R.raw.beauty_vertex, R.raw.beauty_fragment);
        setFilterType(filterType);
    }

    public BeautyFilter setFlag(int flag) {
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
        GLES20.glUniform1f(glAaCoef, aaCoef);
        GLES20.glUniform1f(glMixCoef, mixCoef);
        GLES20.glUniform1i(glIternum, iternum);
    }

    @Override
    public void onCreated(int mProgram) {
        glAaCoef = GLES20.glGetUniformLocation(mProgram, "uAaCoef");
        glMixCoef = GLES20.glGetUniformLocation(mProgram, "uMixCoef");
        glIternum = GLES20.glGetUniformLocation(mProgram, "uIternum");
    }

    @Override
    public void onChanged(int width, int height) {
    }
}
