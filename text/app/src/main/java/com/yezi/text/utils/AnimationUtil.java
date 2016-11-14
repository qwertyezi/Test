package com.yezi.text.utils;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by yezi on 16-11-7.
 */

public class AnimationUtil {

    public static Animator  moveScrollViewToX(View view,int time,int delayTime,int toX) {
        ValueAnimator animator = ObjectAnimator.ofFloat(view,"scrollX",toX);
        animator.setDuration(time);
        animator.setStartDelay(delayTime);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        return animator;
    }
}
