package com.yezi.text.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yezi.text.dagger2.MainComponent;
import com.yezi.text.dagger2.Poetry;

import javax.inject.Inject;

public class Dagger2OtherActivity extends AppCompatActivity {

    private TextView mTextView;

    @Inject
    Poetry mPoetry;

    @Inject
    Gson mGson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTextView = new TextView(this);
        setContentView(mTextView);

        MainComponent.getInstance().inject(this);


        initViews();
    }

    private void initViews() {
        String json = mGson.toJson(mPoetry);
        String text = json + ",mPoetry:" + mPoetry;

        mTextView.setText(text);
    }
}
