package com.yezi.text.widget.likeanimation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.yezi.text.R;

public class LikeButtonView extends FrameLayout implements View.OnClickListener {
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

    private CircleView vCircle;
    private ImageView ivImage;

    private boolean isClicked = false;
    private AnimatorSet mAnimatorSet;
    private DotsView vDotsView;

    public LikeButtonView(Context context) {
        this(context, null);
    }

    public LikeButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_like_button, this, true);
        vCircle = (CircleView) findViewById(R.id.vCircle);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        vDotsView = (DotsView) findViewById(R.id.vDotsView);
        setOnClickListener(this);
        mAnimatorSet = new AnimatorSet();
    }

    @Override
    public void onClick(View v) {
        if (mAnimatorSet.isRunning())
            return;

        isClicked = !isClicked;

        ivImage.setImageResource(isClicked ? R.drawable.ic_star_rate_on : R.drawable.ic_star_rate_off);

        if (isClicked) {
            ivImage.animate().cancel();
            ivImage.setScaleX(0);
            ivImage.setScaleY(0);
            vCircle.setInnerCircleRadiusProgress(0);
            vCircle.setOuterCircleRadiusProgress(0);

            ObjectAnimator outerCircleAnimator =
                    ObjectAnimator.ofFloat(vCircle, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            outerCircleAnimator.setDuration(250);
            outerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator innerCircleAnimator =
                    ObjectAnimator.ofFloat(vCircle, CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            innerCircleAnimator.setDuration(200);
            innerCircleAnimator.setStartDelay(200);
            innerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator imageSacleXAnimator = ObjectAnimator.ofFloat(ivImage, "scaleX", 0.2f, 1f);
            imageSacleXAnimator.setDuration(350);
            imageSacleXAnimator.setStartDelay(250);
            imageSacleXAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator imageSacleYAnimator = ObjectAnimator.ofFloat(ivImage, "scaleY", 0.2f, 1f);
            imageSacleYAnimator.setDuration(350);
            imageSacleYAnimator.setStartDelay(250);
            imageSacleYAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(vDotsView, DotsView.DOTS_PROGRESS, 0, 1f);
            dotsAnimator.setDuration(900);
            dotsAnimator.setStartDelay(50);
            dotsAnimator.setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);

            mAnimatorSet.playTogether(
                    outerCircleAnimator,
                    innerCircleAnimator,
                    imageSacleXAnimator,
                    imageSacleYAnimator,
                    dotsAnimator
            );

            mAnimatorSet.start();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mAnimatorSet.isRunning())
            return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ivImage.animate().scaleX(0.7f).scaleY(0.7f).setDuration(150).setInterpolator(DECCELERATE_INTERPOLATOR);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                ivImage.animate().scaleX(1f).scaleY(1f).setDuration(150).setInterpolator(DECCELERATE_INTERPOLATOR);
                if (event.getX() > 0 && event.getX() <= getWidth() &&
                        event.getY() > 0 && event.getY() <= getHeight()) {
                    performClick();
                }
                break;
        }
        return true;
    }
}
