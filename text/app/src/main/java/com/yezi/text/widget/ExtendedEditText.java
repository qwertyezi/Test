package com.yezi.text.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class ExtendedEditText extends EditText {

    public interface onBackKeyListener {
        boolean onBackKey();
    }

    private onBackKeyListener mBackKeyListener;

    public void setOnBackKeyListener(onBackKeyListener listener) {
        mBackKeyListener = listener;
    }

    public ExtendedEditText(Context context) {
        super(context);
    }

    public ExtendedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (mBackKeyListener != null) {
                return mBackKeyListener.onBackKey();
            }
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
