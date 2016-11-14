package com.yezi.text.widget.RotatePager;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

public class RotatePager extends FrameLayout {

    private static final int ANIMATOR_TIME = 200;

    private RotatePagerAdapter mRotatePagerAdapter;

    public RotatePager(Context context) {
        this(context, null);
    }

    public RotatePager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotatePager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setAdapter(RotatePagerAdapter adapter) {
        if (adapter == null) {
            return;
        }
        mRotatePagerAdapter = adapter;
        mRotatePagerAdapter.registerDataSetObserver(mRotatePagerDataSetObserver);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int downX = 0, downY = 0, upX = 0, upY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                upX = (int) ev.getX();
                upY = (int) ev.getY();
                break;
        }
        if (Math.abs(downX - upX) < dp2px(getContext(), 3) &&
                Math.abs(downY - upY) < dp2px(getContext(), 3)) {
            return super.dispatchTouchEvent(ev);
        } else {
            return true;
        }
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int downX = 0, upX = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                upX = (int) ev.getX();
                break;
        }
        if (upX - downX > 20) {
            moveOneStep();
        }
        return true;
    }

    private void moveOneStep() {
        WrappingImage image = (WrappingImage) getChildAt(getChildCount() - 1);
        AnimatorSet set = new AnimatorSet();

        for (int i = 0; i < getChildCount() - 1; i++) {
            ((WrappingImage) getChildAt(i)).setPosition(i + 1);

            WrappingImage m = (WrappingImage) getChildAt(i);
            ValueAnimator pAni = getAnimator(m, true, m.getImageLastLeft(), m.getImageLeft());
            ValueAnimator wAni = getAnimator(m, false, m.getImageLastWidth(), m.getImageWidth());
            set.play(pAni).with(wAni);
        }

        image.setPosition(0);
        removeView(image);
        addView(image);

        ValueAnimator positionAni = getAnimator(image, true, image.getImageLastLeft(), image.getImageLeft());
        ValueAnimator widthAni = getAnimator(image, false,
                image.getImageLastWidth(), image.getImageWidth());
        set.play(positionAni).with(widthAni);
        set.start();
    }

    private void moveImage(WrappingImage image) {
        ViewGroup vg = RotatePager.this;
        AnimatorSet set = new AnimatorSet();

        for (int i = image.getPosition() + 1; i < vg.getChildCount(); i++) {
            ((WrappingImage) vg.getChildAt(i)).setPosition(i - 1);

            WrappingImage m = (WrappingImage) vg.getChildAt(i);
            ValueAnimator pAni = getAnimator(m, true, m.getImageLastLeft(), m.getImageLeft());
            ValueAnimator wAni = getAnimator(m, false, m.getImageLastWidth(), m.getImageWidth());
            set.play(pAni).with(wAni);
        }

        image.setPosition(vg.getChildCount() - 1);
        vg.removeView(image);
        vg.addView(image);

        ValueAnimator positionAni = getAnimator(image, true, image.getImageLastLeft(), 0);
        ValueAnimator widthAni = getAnimator(image, false,
                image.getImageLastWidth(), image.getImageWidth());
        set.play(positionAni).with(widthAni);
        set.start();
    }

    private ValueAnimator getAnimator(final View view, final boolean isPositionAnimator, int from, int to) {
        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final LayoutParams lp = (LayoutParams) view.getLayoutParams();
                if (isPositionAnimator) {
                    lp.leftMargin = (int) animation.getAnimatedValue();
                } else {
                    lp.width = (int) animation.getAnimatedValue();
                    lp.height = (int) animation.getAnimatedValue();
                }
                view.setLayoutParams(lp);
            }
        });

        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(ANIMATOR_TIME);
        return animator;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mRotatePagerAdapter != null) {
            mRotatePagerAdapter.unregisterDataSetObserver(mRotatePagerDataSetObserver);
        }
    }

    private DataSetObserver mRotatePagerDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            for (int i = 0; i < mRotatePagerAdapter.getCount(); i++) {
                addView(mRotatePagerAdapter.getView(i, null, RotatePager.this));
            }

            initWrappingImage();
        }

        private void initWrappingImage() {
            for (int i = 0; i < mRotatePagerAdapter.getCount(); i++) {
                WrappingImage image = (WrappingImage) RotatePager.this.getChildAt(i);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) image.getLayoutParams();
                lp.width = image.getImageWidth();
                lp.height = image.getImageWidth();
                lp.gravity = Gravity.BOTTOM;
                lp.leftMargin = image.getImageLeft();
                image.setLayoutParams(lp);

                setClickToImage(image);
            }
        }

        private void setClickToImage(final WrappingImage image) {
            image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveImage(image);
                }
            });
        }

    };
}
