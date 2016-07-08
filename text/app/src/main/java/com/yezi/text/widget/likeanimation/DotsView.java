package com.yezi.text.widget.likeanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.util.Property;

import java.util.Random;

public class DotsView extends View {
    private static final int DOTS_COUNT = 7;
    private static final int OUTER_DOTS_POSITION_ANGLE = 360 / DOTS_COUNT;
    private static final int[] colors = {Color.RED, Color.rgb(255, 165, 0), Color.YELLOW, Color.GREEN,
            Color.CYAN, Color.BLUE, Color.rgb(160, 32, 240)};
    private static final Paint[] circlePaints = new Paint[colors.length];
    private static Handler mHandler;

    private Random mRandom;
    private int isRandomColor = 0;

    private int centerX;
    private int centerY;

    private float maxOuterDotsRadius;
    private float maxInnerDotsRadius;
    private float maxDotSize;

    private float currentProgress = 0;

    private float currentRadius1 = 0;
    private float currentDotSize1 = 0;

    private float currentDotSize2 = 0;
    private float currentRadius2 = 0;

    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private Runnable mRunnable;

    public DotsView(Context context) {
        this(context, null);
    }

    public DotsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        for (int i = 0; i < circlePaints.length; i++) {
            circlePaints[i] = new Paint();
            circlePaints[i].setStyle(Paint.Style.FILL);
        }
        mRandom = new Random();
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this, 250);
                if (currentProgress < 0.5f) {
                    float progress = (float) LikeAnimationUtils.mapValueFromRangeToRange(currentProgress, 0f, 0.5f, 0, 1f);
                    for (int i = 0; i < circlePaints.length; i++) {
                        circlePaints[i].setColor((Integer)
                                argbEvaluator.evaluate(progress, getRandomColor(), getRandomColor()));
                    }
                } else {
                    float progress = (float) LikeAnimationUtils.mapValueFromRangeToRange(currentProgress, 0.5f, 1f, 0, 1f);
                    for (int i = 0; i < circlePaints.length; i++) {
                        circlePaints[i].setColor((Integer)
                                argbEvaluator.evaluate(progress, getRandomColor(), getRandomColor()));
                    }
                }
            }
        };
    }

    private int getRandomColor() {
        return colors[mRandom.nextInt(7)];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        maxDotSize = 20;
        maxOuterDotsRadius = w / 2 - maxDotSize * 2;
        maxInnerDotsRadius = 0.8f * maxOuterDotsRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawOuterDotsFrame(canvas);
        drawInnerDotsFrame(canvas);
    }

    private void drawOuterDotsFrame(Canvas canvas) {
        for (int i = 0; i < DOTS_COUNT; i++) {
            int cX = (int) (centerX + currentRadius1 * Math.cos(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            int cY = (int) (centerY + currentRadius1 * Math.sin(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            canvas.drawCircle(cX, cY, currentDotSize1, circlePaints[i % circlePaints.length]);
        }
    }

    private void drawInnerDotsFrame(Canvas canvas) {
        for (int i = 0; i < DOTS_COUNT; i++) {
            int cX = (int) (centerX + currentRadius2 * Math.cos((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180));
            int cY = (int) (centerY + currentRadius2 * Math.sin((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180));
            canvas.drawCircle(cX, cY, currentDotSize2, circlePaints[(i + 1) % circlePaints.length]);
        }
    }

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;

        updateInnerDotsPosition();
        updateOuterDotsPosition();
        updateDotsPaints();
        updateDotsAlpha();

        postInvalidate();
    }

    public float getCurrentProgress() {
        return currentProgress;
    }

    private void updateInnerDotsPosition() {
        if (currentProgress < 0.3f) {
            this.currentRadius2 = (float) LikeAnimationUtils.mapValueFromRangeToRange(currentProgress, 0, 0.3f, 0.f, maxInnerDotsRadius);
        } else {
            this.currentRadius2 = maxInnerDotsRadius;
        }

        if (currentProgress < 0.2) {
            this.currentDotSize2 = maxDotSize;
        } else if (currentProgress < 0.5) {
            this.currentDotSize2 = (float) LikeAnimationUtils.mapValueFromRangeToRange(currentProgress, 0.2f, 0.5f, maxDotSize, 0.3 * maxDotSize);
        } else {
            this.currentDotSize2 = (float) LikeAnimationUtils.mapValueFromRangeToRange(currentProgress, 0.5f, 1f, maxDotSize * 0.3f, 0);
        }
    }

    private void updateOuterDotsPosition() {
        if (currentProgress < 0.3f) {
            this.currentRadius1 = (float) LikeAnimationUtils.mapValueFromRangeToRange(currentProgress, 0.0f, 0.3f, 0, maxOuterDotsRadius * 0.8f);
        } else {
            this.currentRadius1 = (float) LikeAnimationUtils.mapValueFromRangeToRange(currentProgress, 0.3f, 1f, 0.8f * maxOuterDotsRadius, maxOuterDotsRadius);
        }

        if (currentProgress < 0.7) {
            this.currentDotSize1 = maxDotSize;
        } else {
            this.currentDotSize1 = (float) LikeAnimationUtils.mapValueFromRangeToRange(currentProgress, 0.7f, 1f, maxDotSize, 0);
        }
    }

    private void updateDotsPaints() {
        if (currentProgress != 1) {
            ++isRandomColor;
        } else {
            isRandomColor = 0;
        }

        if (isRandomColor == 1) {
            mHandler.post(mRunnable);
        }

        if (currentProgress == 1) {
            mHandler.removeCallbacks(mRunnable);
            isRandomColor = 0;
        }
    }

    private void updateDotsAlpha() {
        float progress = (float) LikeAnimationUtils.clamp(currentProgress, 0.6f, 1f);
        int alpha = (int) LikeAnimationUtils.mapValueFromRangeToRange(progress, 0.6f, 1f, 255, 0);
        circlePaints[0].setAlpha(alpha);
        circlePaints[1].setAlpha(alpha);
        circlePaints[2].setAlpha(alpha);
        circlePaints[3].setAlpha(alpha);
    }

    public static final Property<DotsView, Float> DOTS_PROGRESS = new Property<DotsView, Float>(Float.class, "dotsProgress") {
        @Override
        public Float get(DotsView object) {
            return object.getCurrentProgress();
        }

        @Override
        public void set(DotsView object, Float value) {
            object.setCurrentProgress(value);
        }
    };
}
