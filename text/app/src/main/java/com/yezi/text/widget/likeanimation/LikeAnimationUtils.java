package com.yezi.text.widget.likeanimation;

public class LikeAnimationUtils {
    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }

    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }
}
