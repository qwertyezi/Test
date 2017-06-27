package com.yezi.text.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.yezi.text.R;

public class SearchViewActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview);
        Log.i("LUCK", "B-->onCreate");
    }

    public void TestClick(View view) {
        this.startActivity(new Intent(this, BezierActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LUCK", "B-->onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LUCK", "B-->onResume");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("LUCK", "B-->onNewIntent");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LUCK", "B-->onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LUCK", "B-->onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LUCK", "B-->onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i("LUCK", "B-->onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.i("LUCK", "B-->onRestoreInstanceState");
    }
}
