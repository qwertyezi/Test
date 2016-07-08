package com.yezi.text.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class QuadBezierView extends View {

    private Paint mPaint;
    private Path mPath;
    private Point mStartPoint;
    private Point mEndPoint;
    private Point mAssistPoint;

    public QuadBezierView(Context context) {
        this(context, null);
    }

    public QuadBezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuadBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mStartPoint = new Point(300, 300);
        mEndPoint = new Point(900, 300);
        mAssistPoint = new Point(600, 600);

        mPath=new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(mStartPoint.x, mStartPoint.y);
        mPath.quadTo(mAssistPoint.x, mAssistPoint.y, mEndPoint.x, mEndPoint.y);
        canvas.drawPath(mPath, mPaint);
        canvas.drawPoint(mAssistPoint.x, mAssistPoint.y, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mAssistPoint.x = (int) event.getX();
                mAssistPoint.y = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
