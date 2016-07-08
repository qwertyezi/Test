package com.yezi.text.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.ObjectAnimator;
import com.yezi.text.R;
import com.yezi.text.widget.StartUpView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class StartUpPtrHeader extends FrameLayout implements PtrUIHandler {

    private StartUpView mHeader;
    private int mHeight;

    public StartUpPtrHeader(Context context) {
        this(context, null);
    }

    public StartUpPtrHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StartUpPtrHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.refresh_header, this);
        mHeader = (StartUpView) view.findViewById(R.id.header);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mHeader.startBlink();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mHeader.stopBlink();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        float factor = (float) 45 / mHeight;
        mHeader.rotate(ptrIndicator.getCurrentPosY() * factor);
    }
}
