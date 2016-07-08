package com.yezi.text.widget.likeanimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.util.Property;

public class CircleView extends View {
    private static final int START_COLOR = 0xFFFF5722;
    private static final int END_COLOR = 0xFFFFC107;

    private Paint circlePaint = new Paint();
    private Paint maskPaint = new Paint();

    private int maxCircleSize;

    private Bitmap tempBitmap;
    private Canvas tempCanvas;

    private float outerCircleRadiusProgress = 0f;
    private float innerCircleRadiusProgress = 0f;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint.setStyle(Paint.Style.FILL);
//        circlePaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        circlePaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxCircleSize = getMeasuredWidth() / 2;
        tempBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        tempCanvas = new Canvas(tempBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        tempCanvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
        tempCanvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                outerCircleRadiusProgress * maxCircleSize, circlePaint);
        tempCanvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2,
                innerCircleRadiusProgress * maxCircleSize, maskPaint);
        canvas.drawBitmap(tempBitmap, 0, 0, null);
    }

    public float getOuterCircleRadiusProgress() {
        return outerCircleRadiusProgress;
    }

    public void setOuterCircleRadiusProgress(float outerCircleRadiusProgress) {
        this.outerCircleRadiusProgress = outerCircleRadiusProgress;
        updateCircleColor();
        postInvalidate();
    }

    public float getInnerCircleRadiusProgress() {
        return innerCircleRadiusProgress;
    }

    public void setInnerCircleRadiusProgress(float innerCircleRadiusProgress) {
        this.innerCircleRadiusProgress = innerCircleRadiusProgress;
        postInvalidate();
    }

    private void updateCircleColor() {
        float colorProgress = (float) LikeAnimationUtils.clamp(outerCircleRadiusProgress, 0.5f, 1f);
        this.circlePaint.setColor((Integer) new ArgbEvaluator().evaluate(colorProgress, START_COLOR, END_COLOR));
    }

    public static final Property<CircleView, Float> INNER_CIRCLE_RADIUS_PROGRESS =
            new Property<CircleView, Float>(Float.class, "innerCircleRadiusProgress") {
                @Override
                public Float get(CircleView object) {
                    return object.getInnerCircleRadiusProgress();
                }

                @Override
                public void set(CircleView object, Float value) {
                    object.setInnerCircleRadiusProgress(value);
                }
            };

    public static final Property<CircleView, Float> OUTER_CIRCLE_RADIUS_PROGRESS =
            new Property<CircleView, Float>(Float.class, "outerCircleRadiusProgress") {
                @Override
                public Float get(CircleView object) {
                    return object.getOuterCircleRadiusProgress();
                }

                @Override
                public void set(CircleView object, Float value) {
                    object.setOuterCircleRadiusProgress(value);
                }
            };
}
