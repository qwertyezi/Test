package com.yezi.text.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yezi.text.R;
import com.yezi.text.utils.ScreentUtil;

import java.util.Timer;
import java.util.TimerTask;

public class QQBezierView extends View {

    private static final float CircleRadius = 30f;
    private static final float DismissCircleRadius = CircleRadius / 3;
    private static final float DismissRadius = 300f;
    private static final int BallingTime = 3;
    private static float TranslateX = 0f;
    private static float TranslateY = 0f;

    private enum BallState {STRETCH, DISMISS, EXPLODE}

    private Paint mPaint;
    private Path mPath;

    private PointF mTouchPoint;
    private PointF mOneStartPoint;
    private PointF mOneEndPoint;
    private PointF mTwoStartPoint;
    private PointF mTwoEndPoint;
    private PointF mAssistPoint;

    private PointF mTouchUpPoint;

    private float mCurrentCircleRadius;

    private Context mContext;
    private BallState mBallState;

    private BallingRunnable mBallingRunnable;
    private onExplodeListener mListener;

    public QQBezierView(Context context) {
        this(context, null);
    }

    public QQBezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        init();
    }

    public void setTranslate(float x, float y) {
        TranslateX = x;
        TranslateY = y;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();

        mTouchPoint = new PointF();
        mOneStartPoint = new PointF();
        mOneEndPoint = new PointF();
        mTwoStartPoint = new PointF();
        mTwoEndPoint = new PointF();
        mAssistPoint = new PointF();
        mTouchUpPoint = new PointF();

        mBallState = BallState.STRETCH;

        mBallingRunnable = new BallingRunnable();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(TranslateX, TranslateY - ScreentUtil.getStatusBarHeight(mContext));
        switch (mBallState) {
            case STRETCH:
                mPath.reset();
                mPath.moveTo(mOneStartPoint.x, mOneStartPoint.y);
                mPath.quadTo(mAssistPoint.x, mAssistPoint.y, mOneEndPoint.x, mOneEndPoint.y);
                mPath.lineTo(mTwoEndPoint.x, mTwoEndPoint.y);
                mPath.quadTo(mAssistPoint.x, mAssistPoint.y, mTwoStartPoint.x, mTwoStartPoint.y);
                mPath.close();
                canvas.drawPath(mPath, mPaint);
                canvas.save();

                canvas.drawCircle(0, 0, mCurrentCircleRadius, mPaint);
                canvas.save();
                canvas.drawCircle(mTouchPoint.x, mTouchPoint.y, CircleRadius, mPaint);
                canvas.save();
                break;
            case DISMISS:
                canvas.drawCircle(mTouchPoint.x, mTouchPoint.y, CircleRadius, mPaint);
                break;
            case EXPLODE:

                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mTouchPoint.x = event.getRawX() - TranslateX;
                mTouchPoint.y = event.getRawY() - TranslateY;
                judgeState(MotionEvent.ACTION_MOVE);
                break;
            case MotionEvent.ACTION_UP:
                mTouchUpPoint.x = event.getRawX() - TranslateX;
                mTouchUpPoint.y = event.getRawY() - TranslateY;
                judgeState(MotionEvent.ACTION_UP);
                break;
        }
        return true;
    }

    private void judgeState(int eventAction) {
        float distance = (float) Math.sqrt(mTouchPoint.x * mTouchPoint.x + mTouchPoint.y * mTouchPoint.y);
        if (eventAction == MotionEvent.ACTION_MOVE) {
            if (distance > DismissRadius) {
                mBallState = BallState.DISMISS;
            } else {
                mBallState = BallState.STRETCH;
                calculateBezierPoint();
            }
        } else if (eventAction == MotionEvent.ACTION_UP) {
            if (distance > DismissRadius) {
                mBallState = BallState.EXPLODE;
                startExplodeAnimation();
            } else {
                mBallState = BallState.STRETCH;
                mBallingRunnable.startAnimation(BallingTime, mTouchUpPoint);
            }
        }
        postInvalidate();
    }

    private void startExplodeAnimation() {
        final WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        final ImageView imageView = new ImageView(mContext);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = (int) (mTouchUpPoint.x + TranslateX - 35);
        params.y = (int) (mTouchUpPoint.y + TranslateY - 35 - ScreentUtil.getStatusBarHeight(mContext));
        params.width = 70;
        params.height = 70;
        windowManager.addView(imageView, params);
        imageView.setImageResource(R.drawable.xplode_animation);
        ((AnimationDrawable) imageView.getDrawable()).start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ((AnimationDrawable) imageView.getDrawable()).stop();
                windowManager.removeView(imageView);
                mListener.onExplode();
            }
        }, 400);
    }

    public interface onExplodeListener {
        void onExplode();
    }

    public void setOnExplodeListener(onExplodeListener listener) {
        mListener = listener;
    }

    private void calculateBezierPoint() {
        float distance = (float) Math.sqrt(mTouchPoint.x * mTouchPoint.x + mTouchPoint.y * mTouchPoint.y);

        //根据比例计算中心小球的半径
        mCurrentCircleRadius = (float) (CircleRadius - distance
                / DismissRadius * (CircleRadius - DismissCircleRadius));

        //计算中心小球上的贝塞尔控制点
        float k = mTouchPoint.y / mTouchPoint.x;
        float temp = (float) Math.sqrt(k * k + 1);
        mOneStartPoint.x = k * mCurrentCircleRadius / temp;
        mOneStartPoint.y = -(mCurrentCircleRadius / temp);
        mTwoStartPoint.x = -mOneStartPoint.x;
        mTwoStartPoint.y = -mOneStartPoint.y;

        //计算外部小球上的贝塞尔控制点
        mOneEndPoint.x = k * CircleRadius / temp + mTouchPoint.x;
        mOneEndPoint.y = -(CircleRadius / temp) + mTouchPoint.y;
        mTwoEndPoint.x = -(k * CircleRadius / temp) + mTouchPoint.x;
        mTwoEndPoint.y = CircleRadius / temp + mTouchPoint.y;

        //
        mAssistPoint.x = mTouchPoint.x / 2;
        mAssistPoint.y = mTouchPoint.y / 2;
    }

    class BallingRunnable implements Runnable {
        long mDuration;
        long mStartTime;
        boolean mIsFinished = true;
        float mFactor = 0f;
        PointF mOnePoint;
        PointF mTwoPoint;
        PointF mThreePoint;
        int mIndex = 0;

        public void abortAnimation() {
            mIsFinished = true;
        }

        public boolean isFinished() {
            return mIsFinished;
        }

        @Override
        public void run() {
            if (!mIsFinished) {
                mFactor = ((float) SystemClock.currentThreadTimeMillis() - (float) mStartTime) /
                        (float) mDuration * (mOnePoint.x > 0 ? 1 : -1);
                if (Math.abs(mFactor) <= 1.0f) {
                    mTouchPoint.x = mOnePoint.x - mFactor * Math.abs(mOnePoint.x - mTwoPoint.x);
                    mTouchPoint.y = mOnePoint.y / mOnePoint.x * mTouchPoint.x;
                    calculateBezierPoint();
                    QQBezierView.this.invalidate();
                    QQBezierView.this.post(this);
                    return;
                } else {
                    ++mIndex;
                    if (mIndex == 1) {
                        mOnePoint = mTwoPoint;
                        mTwoPoint = mThreePoint;
                        mStartTime = SystemClock.currentThreadTimeMillis();
                        QQBezierView.this.post(this);
                        return;
                    } else if (mIndex == 2) {
                        mOnePoint = mThreePoint;
                        mTwoPoint = new PointF(0, 0);
                        mStartTime = SystemClock.currentThreadTimeMillis();
                        QQBezierView.this.post(this);
                        return;
                    }
                }

                mIsFinished = true;
                mListener.onExplode();
            }
        }

        private void calculatePoint() {
            mTwoPoint = new PointF(-mOnePoint.x / 2, -mOnePoint.y / 2);
            mThreePoint = new PointF(-mTwoPoint.x / 2, -mTwoPoint.y / 2);
        }

        public void startAnimation(long durationTime, PointF onePoint) {
            mStartTime = SystemClock.currentThreadTimeMillis();
            mDuration = durationTime;
            mOnePoint = onePoint;
            mIndex = 0;
            calculatePoint();
            mIsFinished = false;
            QQBezierView.this.post(this);
        }
    }
}
