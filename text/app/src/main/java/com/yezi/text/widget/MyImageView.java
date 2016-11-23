package com.yezi.text.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yezi.text.R;

public class MyImageView extends View {

    private Bitmap mBitmap;
    private Matrix mMatrix;

    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imageview_scaletype);
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0, 0, null);

        mMatrix.reset();
        Log.i("LUCK", mMatrix.toString());
        mMatrix.setScale(0.5f, 0.5f, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        Log.i("LUCK", mMatrix.toString());
        mMatrix.preRotate(45.0f, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        mMatrix.preRotate(-90.0f, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);

        canvas.drawBitmap(mBitmap, mMatrix, null);
    }
}
