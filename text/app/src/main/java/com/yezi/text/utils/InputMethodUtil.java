package com.yezi.text.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class InputMethodUtil {

    public static void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }
    }

    public static void hideSoftKeyboard(View view) {
        view.clearFocus();
        InputMethodManager imm = (InputMethodManager)
                view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void toggleSoftKeyboard(View view, boolean show) {
        if (show) {
            showSoftKeyboard(view);
        } else {
            hideSoftKeyboard(view);
        }
    }
}
