package com.yezi.text.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yezi.text.dagger2.MainComponent;
import com.yezi.text.dagger2.Poetry;

import javax.inject.Inject;

public class Dagger2Activity extends AppCompatActivity {

    private TextView mTextView;

    @Inject
    Poetry mPoetry;

    @Inject
    Gson mGson;
    private Button mButton;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTextView = new TextView(this);
        mButton = new Button(this);
        mButton.setText("Dagger2OtherActivity");
        mButton.setOnClickListener(v -> this.startActivity(new Intent(this, Dagger2OtherActivity.class)));
        mLinearLayout = new LinearLayout(this);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.addView(mTextView);
        mLinearLayout.addView(mButton);

        setContentView(mLinearLayout);

        MainComponent.getInstance().inject(this);


        initViews();
    }

    private void initViews() {
        String json = mGson.toJson(mPoetry);
        String text = json + ",mPoetry:" + mPoetry;

        mTextView.setText(text);
    }
}
