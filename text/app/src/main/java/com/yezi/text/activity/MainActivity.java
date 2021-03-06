package com.yezi.text.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yezi.text.R;
import com.yezi.text.widget.MyRecycleview;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btRecycleview;
    private Button btSearch;
    private Button btRecycleviewItemTouchHelper;
    private Button btStartUpAnimation;
    private Button btImageLoader;
    private Button btLikeAnimation;
    private Button btAnnotationProcessing;
    private Button btCalendar;
    private TextView mTextRxJava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btSearch = (Button) findViewById(R.id.searchview);
        btRecycleview = (Button) findViewById(R.id.recycleview_adapter_animation);
        btRecycleviewItemTouchHelper = (Button) findViewById(R.id.recycleview_itemtouchhelper);
        btStartUpAnimation = (Button) findViewById(R.id.canvas);
        btImageLoader = (Button) findViewById(R.id.imageloader);
        btLikeAnimation = (Button) findViewById(R.id.likeanimation);
        btAnnotationProcessing = (Button) findViewById(R.id.annotationProcessing);
        btCalendar = (Button) findViewById(R.id.btn_calendar);

        btSearch.setOnClickListener(this);
        btRecycleview.setOnClickListener(this);
        btRecycleviewItemTouchHelper.setOnClickListener(this);
        btStartUpAnimation.setOnClickListener(this);
        btImageLoader.setOnClickListener(this);
        btLikeAnimation.setOnClickListener(this);
        btAnnotationProcessing.setOnClickListener(this);
        btCalendar.setOnClickListener(this);

        ((TextView) findViewById(R.id.text_brand)).setText(Build.BRAND);

        Log.i("LUCK", "A-->onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LUCK", "A-->onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LUCK", "A-->onResume");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("LUCK", "A-->onNewIntent");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LUCK", "A-->onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LUCK", "A-->onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LUCK", "A-->onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i("LUCK", "A-->onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.i("LUCK", "A-->onRestoreInstanceState");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchview:
                this.startActivity(new Intent(this, SearchViewActivity.class));
                break;
            case R.id.recycleview_adapter_animation:
                this.startActivity(new Intent(this, MyRecycleview.class));
                break;
            case R.id.recycleview_itemtouchhelper:
                this.startActivity(new Intent(this, RecycleviewItemtouchhelperActivity.class));
                break;
            case R.id.canvas:
                this.startActivity(new Intent(this, CanvasActivity.class));
                break;
            case R.id.imageloader:
                this.startActivity(new Intent(this, ImageLoaderActivity.class));
                break;
            case R.id.likeanimation:
                this.startActivity(new Intent(this, LikeAnimationActivity.class));
                break;
            case R.id.annotationProcessing:
                this.startActivity(new Intent(this, OkhttpTestActivity.class));
                break;
            case R.id.btn_calendar:
                this.startActivity(new Intent(this, CalendarActivity.class));
                break;
        }
    }

    public void BezierClick(View view) {
        this.startActivity(new Intent(this, BezierActivity.class));
    }

    public void DataBindingClick(View view) {
        this.startActivity(new Intent(this, DataBindingActivity.class));
    }

    public void DataBindingClick2(View view) {
        this.startActivity(new Intent(this, DataBinding2Activity.class));
    }

    public void TouchClick(View view) {
        this.startActivity(new Intent(this, TouchActivity.class));
    }

    public void OkHttpClick(View view) {
        this.startActivity(new Intent(this, OkHttpActivity.class));
    }

    public void TransformClick(View view) {
        this.startActivity(new Intent(this, TransformActivity.class));
    }

    public void MyTabLayoutClick(View view) {
        this.startActivity(new Intent(this, MyTabLayoutActivity.class));
    }

    public void RotatePagerClick(View view) {
        this.startActivity(new Intent(this, RotatePagerTestActivity.class));
    }

    public void ImageViewClick(View view) {
        this.startActivity(new Intent(this, ImageViewActivity.class));
    }

    public void SpringRecyclerViewClick(View view) {
        this.startActivity(new Intent(this, SpringRecyclerViewActivity.class));
    }

    public void DatePickerDialogClick(View view) {
        new DatePickerDialog(this, null, 2016, 12, 1).show();
    }

    public void Dagger2Click(View view) {
        this.startActivity(new Intent(this, Dagger2Activity.class));
    }

    public void TestFragmentAdapterClick(View view) {
        this.startActivity(new Intent(this, TestFragmentAdapterActivity.class));
    }
}
