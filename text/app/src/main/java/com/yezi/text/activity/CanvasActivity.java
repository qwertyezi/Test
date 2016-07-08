package com.yezi.text.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yezi.text.R;

import java.util.ArrayList;
import java.util.List;

public class CanvasActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_canvas);
        setContentView(new MyView(this));
    }

    public class MyView extends View {

        private List<PointF> mList;

        private Paint mPaint;

        public MyView(Context context) {
            this(context, null);
        }

        public MyView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init() {
            mList = new ArrayList<>();
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(5);
            mPaint.setStrokeJoin(Paint.Join.BEVEL);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            mList.add(new PointF(event.getX(), event.getY()));
            invalidate();
            return true;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
        }

        @Override
        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);

            for (PointF pointF : mList) {
                canvas.drawPoint(pointF.x, pointF.y, mPaint);
            }

//            canvas.drawCircle(100, 100, 90, mPaint);

//            RectF rectF = new RectF(0, 0, 200, 400);
//            canvas.drawArc(rectF, 0, 90, false, mPaint);

//            canvas.drawLine(10, 10, 200, 300, mPaint);

//            RectF rectF = new RectF(0, 0, 200, 400);
//            canvas.drawOval(rectF, mPaint);

//            canvas.drawPosText("Android777", new float[]{
//                    10, 10, //第一个字母在坐标10,10
//                    20, 20, //第二个字母在坐标20,20
//                    30, 30, //....
//                    40, 40,
//                    50, 50,
//                    60, 60,
//                    70, 70,
//                    80, 80,
//                    90, 90,
//                    100, 100
//            }, mPaint);

//            RectF rectF = new RectF(50, 50, 200, 200);
//            canvas.drawRoundRect(rectF, 20, 200, mPaint);


//            mPaint.setStyle(Paint.Style.STROKE);
//            mPaint.setStrokeWidth(2);
//            canvas.translate(600, 600);
//            canvas.drawCircle(0, 0, 300, mPaint);
//
//            canvas.save();
//            Path path = new Path();
//            path.addArc(new RectF(-250, -250, 250, 250), 215, 325);
//            Paint citePaint = new Paint();
//            citePaint.setColor(Color.RED);
//            citePaint.setTextSize(40);
//            citePaint.setStrokeWidth(2);
//            canvas.drawTextOnPath("http://www.loopeer.com", path, 30, 5, citePaint);
//            canvas.restore();
//
//            Paint linePaint = new Paint();
//            linePaint.setColor(Color.RED);
//            linePaint.setStrokeWidth(2);
//
//            for (int i = 1; i <= 60; i++) {
//                canvas.rotate(360 / 60);
//                if (i % 5 == 0) {
//                    canvas.drawLine(0, 300, 0, 320, linePaint);
//                    canvas.drawText(i + "", 0, 325, linePaint);
//                } else {
//                    canvas.drawLine(0, 300, 0, 310, linePaint);
//                }
//            }
        }
    }
}
