package com.yezi.text.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class TestFragment extends Fragment {

    private static final int[] colors = new int[]{
            Color.BLACK, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN, Color.RED
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setBackgroundColor(colors[new Random().nextInt(colors.length)]);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(30);
        textView.setText("Hello World!");
        return textView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("LUCK", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("LUCK", "onPause");
    }
}
