package com.yezi.text.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yezi.text.R;

public class BezierActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
    }

    public void onGoToList(View view) {
        this.startActivity(new Intent(this, QQBezierListActivity.class));
    }

    public void TestClick(View view) {
        this.startActivity(new Intent(this, SearchViewActivity.class));
    }

}
