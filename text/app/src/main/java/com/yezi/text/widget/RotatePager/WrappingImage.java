package com.yezi.text.widget.RotatePager;


import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

public class WrappingImage extends SimpleDraweeView {

    private static final int IMAGE_WIDTH = 180;
    private static final int WIDTH_DELTA = 30;
    private static final int LEFT_DELTA = 30;

    private int mCount;
    private int mLastPosition;
    private int mPosition;

    public WrappingImage(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public WrappingImage(Context context) {
        super(context);
    }

    public WrappingImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrappingImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setPosition(int position) {
        mLastPosition = mPosition;
        mPosition = position;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getPosition() {
        return mPosition;
    }

    public int getLastPosition() {
        return mLastPosition;
    }

    public int getImageWidth() {
        return IMAGE_WIDTH + WIDTH_DELTA * mPosition;
    }

    public int getImageLastWidth() {
        return IMAGE_WIDTH + WIDTH_DELTA * mLastPosition;
    }

    public int getImageLeft() {
        int left = 0;
        for (int i = mPosition+1; i < mCount; i++) {
            left += IMAGE_WIDTH + WIDTH_DELTA * i;
        }
        return left - LEFT_DELTA * (mCount - 1 - mPosition);
    }

    public int getImageLastLeft() {
        int left = 0;
        for (int i = mLastPosition+1; i < mCount; i++) {
            left += IMAGE_WIDTH + WIDTH_DELTA * i;
        }
        return left - LEFT_DELTA * (mCount - 1 - mLastPosition);
    }

}
